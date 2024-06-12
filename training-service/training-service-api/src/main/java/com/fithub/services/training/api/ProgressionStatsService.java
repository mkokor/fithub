package com.fithub.services.training.api;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsCreateRequest;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsResponse;

public interface ProgressionStatsService {

    ProgressionStatsResponse create(final ProgressionStatsCreateRequest progressionStatCreateRequest) throws Exception;

    List<ProgressionStatsResponse> getProgressionStats(final String clientUuid) throws ApiException;

    ProgressionStatsResponse getLatestStats() throws ApiException;

    List<ProgressionStatsResponse> getRangList(Pageable pageable) throws ApiException;

}