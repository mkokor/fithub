package com.fithub.services.training.core.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SongService;
import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.exception.ThirdPartyApiException;
import com.fithub.services.training.api.model.song.SongRequestCreateRequest;
import com.fithub.services.training.api.model.song.SongRequestResponse;
import com.fithub.services.training.api.model.song.SongSearchResponse;
import com.fithub.services.training.api.model.spotify.AlbumImageResponse;
import com.fithub.services.training.api.model.spotify.AlbumResponse;
import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;
import com.fithub.services.training.api.model.spotify.SpotifyTrackResponse;
import com.fithub.services.training.api.model.spotify.TracksSearchResponse;
import com.fithub.services.training.core.utils.ThirdPartyApiCallable;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.SongRequestEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.SongRequestRepository;
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.mapper.SongMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class SongServiceImpl implements SongService {

    @Value("${spotify.client_id}")
    private String spotifyClientId;

    @Value("${spotify.client_secret}")
    private String spotifyClientSecret;

    private String spotifyAccessToken;
    private final SpotifyApiService spotifyApiService;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final SongRequestRepository songRequestRepository;
    private final SongMapper songMapper;
    private final Validator validator;

    @Autowired
    public SongServiceImpl(SpotifyApiService spotifyApiService, AppointmentRepository appointmentRepository, SongMapper songMapper,
            Validator validator, UserRepository userRepository, SongRequestRepository songRequestRepository) {
        this.spotifyApiService = spotifyApiService;
        this.userRepository = userRepository;
        this.songRequestRepository = songRequestRepository;
        this.songMapper = songMapper;
        this.validator = validator;
        this.appointmentRepository = appointmentRepository;
    }

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

    // This method is made as a mock until security is implemented.
    private UserEntity getAccessTokenOwner() {
        return userRepository.findById("mary-ann").get();
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

    @Override
    public SongRequestResponse createSongRequest(SongRequestCreateRequest songRequestCreateRequest) throws Exception {
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

        SongRequestEntity songRequestEntity = new SongRequestEntity();
        songRequestEntity.setSongSpotifyId(spotifyTrackResponse.getSpotifyId());
        songRequestEntity.setCreatedAt(LocalDateTime.now());
        songRequestEntity.setAppointment(appointment.get());
        songRequestEntity.setCreatedBy(getAccessTokenOwner().getClient());
        songRequestRepository.save(songRequestEntity);

        return songMapper.songRequestEntityToSongRequestResponse(songRequestEntity);
    }

}