package com.fithub.services.training.api.model.progressionstat;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProgressionStatsPageable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNumber;
    private Integer pageSize;
    private String sortFilter;

}