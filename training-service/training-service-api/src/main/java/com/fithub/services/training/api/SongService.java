package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.exception.ThirdPartyApiException;
import com.fithub.services.training.api.model.song.SongSearchResponse;

public interface SongService {

    List<SongSearchResponse> search(Integer pageNumber, Integer pageSize, String songTitleSearchTerm) throws ThirdPartyApiException;

}