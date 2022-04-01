package com.i62.scoring.service;

import com.i62.scoring.exception.BadRequestException;
import com.i62.scoring.model.ScoringRequest;
import com.i62.scoring.model.ScoringResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScoringServiceTest {

    @InjectMocks
    private ScoringService scoringService;

    @Test
    void calculate_success() {
        ScoringRequest request = ScoringRequest.builder()
                .sport("100 meters")
                .result(2.0).build();
        ScoringResponse response = scoringService.calculate(request);
        assertThat(response.getPoints()).isEqualTo(980.0);
    }

    @Test
    void calculate_invalid_sport_name() {
        ScoringRequest request = ScoringRequest.builder()
                .sport("Football").build();
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> scoringService.calculate(request));
        assertThat(exception.getCode()).isEqualTo("bad_request");
        assertThat(exception.getMessage()).isEqualTo("Sport name is invalid!");
    }

    @Test
    void calculate_invalid_result_score() {
        ScoringRequest request = ScoringRequest.builder()
                .sport("High jump")
                .result(300.0).build();
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> scoringService.calculate(request));
        assertThat(exception.getMessage()).isEqualTo("Result score is invalid!");
    }
}