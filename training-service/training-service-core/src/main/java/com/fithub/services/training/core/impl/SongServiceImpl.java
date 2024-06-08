package com.fithub.services.training.core.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SongService;
import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.exception.ThirdPartyApiException;
import com.fithub.services.training.api.model.external.spotify.AlbumImageResponse;
import com.fithub.services.training.api.model.external.spotify.AlbumResponse;
import com.fithub.services.training.api.model.external.spotify.SpotifyAccessTokenResponse;
import com.fithub.services.training.api.model.external.spotify.SpotifyTrackResponse;
import com.fithub.services.training.api.model.external.spotify.TracksSearchResponse;
import com.fithub.services.training.api.model.song.SongRequestCreateRequest;
import com.fithub.services.training.api.model.song.SongRequestResponse;
import com.fithub.services.training.api.model.song.SongSearchResponse;
import com.fithub.services.training.core.context.UserContext;
import com.fithub.services.training.core.utils.ThirdPartyApiCallable;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.ReservationEntity;
import com.fithub.services.training.dao.model.SongRequestEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.SongRequestRepository;
import com.fithub.services.training.mapper.SongMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    @Value("${spotify.client_id}")
    private String spotifyClientId;

    @Value("${spotify.client_secret}")
    private String spotifyClientSecret;

    private String spotifyAccessToken;
    private final SpotifyApiService spotifyApiService;
    private final AppointmentRepository appointmentRepository;
    private final SongRequestRepository songRequestRepository;
    private final SongMapper songMapper;
    private final Validator validator;

    private void updateSpotifyAuthentication() throws ThirdPartyApiException {
        SpotifyAccessTokenResponse spotifyAccessTokenResponse = this.spotifyApiService.retrieveAccessToken("client_credentials",
                spotifyClientId, spotifyClientSecret);

        spotifyAccessToken = String.format("%s %s", spotifyAccessTokenResponse.getTokenType(), spotifyAccessTokenResponse.getAccessToken());
    }

    private String findAlbumCoverImage(String songSpotifyId, List<SpotifyTrackResponse> tracks) {
        List<List<AlbumImageResponse>> albumImages = tracks.stream().filter(track -> track.getSpotifyId().equals(songSpotifyId))
                .map(SpotifyTrackResponse::getAlbum).filter(Objects::nonNull).map(AlbumResponse::getImages).filter(Objects::nonNull)
                .collect(Collectors.toList());

        return !albumImages.isEmpty() ? albumImages.get(0).get(0).getUrl() : null;
    }

    private <T> T callThirdPartyApi(ThirdPartyApiCallable<T> supplier, String badRequestExceptionMessage) throws Exception {
        try {
            return supplier.apply();
        } catch (ThirdPartyApiException exception) {
            if (exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                updateSpotifyAuthentication();

                try {
                    return supplier.apply();
                } catch (ThirdPartyApiException thirdPartyApiException) {
                    if (thirdPartyApiException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                        throw new BadRequestException(badRequestExceptionMessage);
                    }
                }
            } else if (exception.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new BadRequestException(badRequestExceptionMessage);
            }

            throw exception;
        }
    }

    @Override
    public List<SongSearchResponse> search(Integer pageNumber, Integer pageSize, String songTitleSearchTerm) throws Exception {
        if (songTitleSearchTerm == null || songTitleSearchTerm.length() < 2) {
            throw new BadRequestException("Song search term must contain at least 2 characters.");
        }

        TracksSearchResponse tracksWrapperResponse = callThirdPartyApi(() -> {
            return spotifyApiService.search(spotifyAccessToken, songTitleSearchTerm, List.of("track"), pageNumber, pageSize);
        }, null);

        List<SpotifyTrackResponse> spotifyTracks = tracksWrapperResponse.getTracks().getItems();
        List<SongSearchResponse> songSearchResults = songMapper.spotifyTrackResponsesToSongSearchResponses(spotifyTracks);
        for (SongSearchResponse songSearchResult : songSearchResults) {
            songSearchResult.setAlbumCoverImage(findAlbumCoverImage(songSearchResult.getSpotifyId(), spotifyTracks));
        }

        return songSearchResults;
    }

    private void checkIfClientHasReservation(final ClientEntity clientEntity, final AppointmentEntity appointmentEntity)
            throws BadRequestException {
        final List<ReservationEntity> reservations = clientEntity.getReservations();

        for (ReservationEntity reservationEntity : reservations) {
            if (reservationEntity.getAppointment().getId().equals(appointmentEntity.getId())) {
                return;
            }
        }

        throw new BadRequestException("The client does not have this reservation at this appointment.");
    }

    @Override
    public SongRequestResponse createSongRequest(SongRequestCreateRequest songRequestCreateRequest) throws Exception {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();

        Set<ConstraintViolation<SongRequestCreateRequest>> violations = validator.validate(songRequestCreateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        SpotifyTrackResponse spotifyTrackResponse = callThirdPartyApi(() -> {
            return spotifyApiService.getTrack(spotifyAccessToken, songRequestCreateRequest.getSongSpotifyId());
        }, "Provided Spotify ID of a song is invalid.");

        Optional<AppointmentEntity> appointment = appointmentRepository.findById(songRequestCreateRequest.getAppointmentId());
        if (!appointment.isPresent()) {
            throw new NotFoundException("The appointment with provided ID could not be found.");
        }

        final AppointmentEntity appointmentEntity = appointment.get();
        final ClientEntity clientEntity = userEntity.getClient();
        checkIfClientHasReservation(clientEntity, appointmentEntity);

        SongRequestEntity songRequestEntity = new SongRequestEntity();
        songRequestEntity.setSongSpotifyId(spotifyTrackResponse.getSpotifyId());
        songRequestEntity.setCreatedAt(LocalDateTime.now());
        songRequestEntity.setAppointment(appointment.get());
        songRequestEntity.setCreatedBy(clientEntity);
        songRequestRepository.save(songRequestEntity);

        return songMapper.songRequestEntityToSongRequestResponse(songRequestEntity);
    }

}