package com.i62.scoring.model;

import com.i62.scoring.exception.BadRequestException;

import java.util.Arrays;

public enum SportType {

    SPORT_1("100 meters", 0, 100, true),
    SPORT_2("Long jump", 15, 0, false),
    SPORT_3("Shot put", 100, 0, false),
    SPORT_4("High jump", 10, 0, false),
    SPORT_5("400 metres", 0, 500, true),
    SPORT_6("110 metres hurdles", 0, 150, true),
    SPORT_7("Discus throw", 100, 0, false),
    SPORT_8("Pole vault", 15, 0, false),
    SPORT_9("Javelin throw", 120, 0, false),
    SPORT_10("1500 metres", 0, 2000, false);

    private final String sport;
    private final double bestScore;
    private final double worseScore;
    private final boolean reverse;

    SportType(String sport, double bestScore, double worseScore, boolean reverse) {
        this.sport = sport;
        this.bestScore = bestScore;
        this.worseScore = worseScore;
        this.reverse = reverse;
    }

    public String getSport() {
        return sport;
    }

    public double getBestScore() {
        return bestScore;
    }

    public double getWorseScore() {
        return worseScore;
    }

    public boolean isReverse() {
        return reverse;
    }

    public static void checkIfCorrectRequest(ScoringRequest request) {
        SportType type = Arrays.stream(SportType.values())
                .filter(sportType -> sportType.sport.equalsIgnoreCase(request.getSport()))
                .findAny()
                .orElseThrow(() -> new BadRequestException("Sport name is invalid!"));

        if (type.isReverse()) {
            if (request.getResult() < type.bestScore || request.getResult() > type.worseScore) {
                throw new BadRequestException("Result score is invalid!");
            }
        } else {
            if (request.getResult() > type.bestScore || request.getResult() < type.worseScore) {
                throw new BadRequestException("Result score is invalid!");
            }
        }
    }
}
