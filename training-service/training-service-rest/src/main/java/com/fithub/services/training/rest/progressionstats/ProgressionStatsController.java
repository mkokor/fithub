package com.fithub.services.training.rest.progressionstats;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.ProgressionStatsService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsCreateRequest;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsPageable;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "progression-stats", description = "Progression Stats API")
@RestController
@RequestMapping(value = "progression-stats", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProgressionStatsController {

    private final ProgressionStatsService progressionStatsService;

    @Operation(summary = "Create progression stats")
    @PostMapping
    public ResponseEntity<ProgressionStatsResponse> crateProgressionStats(
            @Valid @RequestBody ProgressionStatsCreateRequest progressionStatsCreateRequest) throws Exception {
        return new ResponseEntity<>(progressionStatsService.create(progressionStatsCreateRequest), HttpStatus.OK);
    }

    @Operation(summary = "Get progression stats for client")
    @GetMapping("/client/{uuid}")
    public ResponseEntity<List<ProgressionStatsResponse>> getProgressionStatsForClient(@PathVariable final String uuid)
            throws ApiException {
        return new ResponseEntity<>(progressionStatsService.getProgressionStats(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Get latest progression stats for authenticated client")
    @GetMapping("/latest")
    public ResponseEntity<ProgressionStatsResponse> getLatestProgressionStats() throws ApiException {
        return new ResponseEntity<>(progressionStatsService.getLatestStats(), HttpStatus.OK);
    }

    @Operation(summary = "Get progression stats rang list")
    @GetMapping("/rang-list")
    public ResponseEntity<List<ProgressionStatsResponse>> getRangList(
            @RequestParam(name = "page_number", required = false, defaultValue = "0") final Integer pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue = "5") final Integer pageSize,
            @RequestParam(name = "sort_by", required = false, defaultValue = "createdAt") final String sortBy) throws ApiException {
        ProgressionStatsPageable pageable = new ProgressionStatsPageable();
        pageable.setPageNumber(pageNumber);
        pageable.setPageSize(pageSize);
        pageable.setSortFilter(sortBy);

        return new ResponseEntity<>(progressionStatsService.getRangList(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get progression stats report")
    @GetMapping("/report")
    public void downloadProgressionStatsExcel(HttpServletResponse response) throws IOException {
        progressionStatsService.generateExcelFromProgressionStats(response);
    }

}