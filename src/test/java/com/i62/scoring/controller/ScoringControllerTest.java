package com.i62.scoring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i62.scoring.exception.BadRequestException;
import com.i62.scoring.model.ScoringRequest;
import com.i62.scoring.model.ScoringResponse;
import com.i62.scoring.service.ScoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ScoringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScoringService service;

    @Test
    void calculate_success() throws Exception {
        String url = "/decathlon/scoring";
        ScoringRequest request = ScoringRequest.builder()
                .sport("100 meters")
                .result(2.0).build();
        given(service.calculate(request)).willReturn(new ScoringResponse(980.0));
        mockMvc.perform(post(url)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.points", is(980.0)));
    }

    @Test
    void calculate_invalid_sport_name() throws Exception {
        String url = "/decathlon/scoring";
        ScoringRequest request = ScoringRequest.builder()
                .sport("100 meters")
                .result(2.0).build();
        given(service.calculate(request)).willThrow(new BadRequestException("Sport name is invalid!"));
        mockMvc.perform(post(url)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", is("bad_request")))
                .andExpect(jsonPath("$.message", is("Sport name is invalid!")));
    }
}