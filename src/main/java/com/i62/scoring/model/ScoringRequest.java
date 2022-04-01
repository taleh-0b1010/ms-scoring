package com.i62.scoring.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ScoringRequest {

    @NotNull
    private String sport;
    @NotNull
    private Double result;
}
