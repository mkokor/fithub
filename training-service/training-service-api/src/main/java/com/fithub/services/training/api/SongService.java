package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.model.song.SongRequestCreateRequest;
import com.fithub.services.training.api.model.song.SongRequestResponse;
import com.fithub.services.training.api.model.song.SongSearchResponse;

public interface SongService {

    List<SongSearchResponse> search(Integer pageNumber, Integer pageSize, String songTitleSearchTerm) throws Exception;

    SongRequestResponse createSongRequest(SongRequestCreateRequest songRequestCreateRequest) throws Exception;

}