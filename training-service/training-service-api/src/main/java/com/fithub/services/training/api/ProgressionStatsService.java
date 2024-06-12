package com.fithub.services.training.api;

import java.io.IOException;
import java.util.List;

import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsCreateRequest;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsPageable;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface ProgressionStatsService {

    ProgressionStatsResponse create(final ProgressionStatsCreateRequest progressionStatCreateRequest) throws Exception;

    List<ProgressionStatsResponse> getProgressionStats(final String clientUuid) throws ApiException;

    ProgressionStatsResponse getLatestStats() throws ApiException;

    List<ProgressionStatsResponse> getRangList(ProgressionStatsPageable progressionStatsPageable) throws ApiException;

    void generateExcelFromProgressionStats(HttpServletResponse response) throws IOException;

}