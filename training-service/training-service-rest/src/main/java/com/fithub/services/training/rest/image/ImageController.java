package com.fithub.services.training.rest.image;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "image", description = "Image API")
@RestController
@RequestMapping(value = "image", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Get paths")
    @GetMapping(value = "/path")
    public ResponseEntity<List<String>> getPaths() throws Exception {
        return new ResponseEntity<>(imageService.getPublicImages(), HttpStatus.OK);
    }

}