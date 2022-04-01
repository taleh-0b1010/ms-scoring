package com.i62.scoring.controller;

import com.i62.scoring.model.ScoringRequest;
import com.i62.scoring.model.ScoringResponse;
import com.i62.scoring.service.ScoringService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decathlon")
public class ScoringController {

    private final ScoringService scoringService;

    public ScoringController(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @PostMapping("/scoring")
    public ScoringResponse calculate(@RequestBody ScoringRequest request) {
        return scoringService.calculate(request);
    }
}
