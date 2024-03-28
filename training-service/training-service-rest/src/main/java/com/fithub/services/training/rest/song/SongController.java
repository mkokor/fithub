package com.fithub.services.training.rest.song;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.SongService;
import com.fithub.services.training.api.model.song.SongSearchResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "song", description = "Song API")
@RestController
@RequestMapping(value = "song")
@AllArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping(name = "/search", produces = "application/json")
    public ResponseEntity<List<SongSearchResponse>> search(
            @RequestParam(name = "page_number", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue = "20") Integer pageSize,
            @Valid @RequestParam("song_title_search_term") String songTitleSearchTerm) throws Exception {
        return new ResponseEntity<>(songService.search(pageNumber, pageSize, songTitleSearchTerm), HttpStatus.OK);
    }

}