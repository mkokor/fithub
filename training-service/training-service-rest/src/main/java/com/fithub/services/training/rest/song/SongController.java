package com.fithub.services.training.rest.song;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.SongService;
import com.fithub.services.training.api.model.song.SongRequestCreateRequest;
import com.fithub.services.training.api.model.song.SongRequestResponse;
import com.fithub.services.training.api.model.song.SongSearchResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "song", description = "Song API")
@RestController
@RequestMapping(value = "song", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class SongController {

    private final SongService songService;

    @Operation(summary = "Search the Spotify")
    @GetMapping(value = "/search")
    public ResponseEntity<List<SongSearchResponse>> search(
            @RequestParam(name = "page_number", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue = "20") Integer pageSize,
            @Valid @RequestParam("song_title_search_term") String songTitleSearchTerm) throws Exception {
        return new ResponseEntity<>(songService.search(pageNumber, pageSize, songTitleSearchTerm), HttpStatus.OK);
    }

    @Operation(summary = "Create a song request")
    @PostMapping(value = "/request")
    public ResponseEntity<SongRequestResponse> createSongRequest(@Valid @RequestBody SongRequestCreateRequest songRequestCreateRequest)
            throws Exception {
        return new ResponseEntity<>(songService.createSongRequest(songRequestCreateRequest), HttpStatus.OK);
    }

}