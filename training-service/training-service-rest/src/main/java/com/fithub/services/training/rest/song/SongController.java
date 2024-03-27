package com.fithub.services.training.rest.song;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.SongService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "song", description = "Song API")
@RestController
@RequestMapping(value = "song")
@AllArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam(name = "song_title_search_term") String songTitleSearchTerm) {
        return new ResponseEntity<>(songService.search(songTitleSearchTerm), HttpStatus.OK);
    }

}