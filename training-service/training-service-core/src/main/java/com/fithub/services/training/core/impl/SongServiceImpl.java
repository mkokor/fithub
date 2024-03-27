package com.fithub.services.training.core.impl;

import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SongService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    @Override
    public String search(String songTitleSearchTerm) {
        return songTitleSearchTerm;
    }

}