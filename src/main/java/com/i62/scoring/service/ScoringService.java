package com.i62.scoring.service;

import com.i62.scoring.model.ScoringRequest;
import com.i62.scoring.model.ScoringResponse;
import com.i62.scoring.model.SportType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class ScoringService {

    private static final int MAX_SCORE = 1000;

    public ScoringResponse calculate(ScoringRequest request) {
        SportType.checkIfCorrectRequest(request);

        double points = Arrays.stream(SportType.values())
                .filter(sportType -> sportType.getSport().equalsIgnoreCase(request.getSport()))
                .map(sportType -> getPoints(sportType, request))
                .findFirst()
                .orElse(Double.NaN);

        log.info("{} points calculated for {}, {}", points, request.getSport(), request.getResult());
        return new ScoringResponse(points);
    }

    private Double getPoints(SportType sportType, ScoringRequest request) {
        double points;
        double percent;

        if (sportType.isReverse()) {
            percent = 100 - (request.getResult() / sportType.getWorseScore() * 100);
        } else {
            percent = request.getResult() / sportType.getBestScore() * 100;
        }

        points = MAX_SCORE * percent / 100;

        return points;
    }
}
