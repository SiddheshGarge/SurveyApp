// SurveyController.java
package com.survey.controller;

import com.survey.dto.SurveyCreateRequest;
import com.survey.dto.SurveyResponse;
import com.survey.dto.ResponseSubmitRequest;
import com.survey.dto.SurveyAnalytics;
import com.survey.entity.*;
import com.survey.repository.*;
import com.survey.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surveys")
@CrossOrigin(origins = "http://localhost:3000")
public class SurveyController {

    private final SurveyService surveyService;
    private final UserRepository userRepository;

    public SurveyController(SurveyService surveyService, UserRepository userRepository) {
        this.surveyService = surveyService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<SurveyResponse>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllActiveSurveys();
        List<SurveyResponse> surveyResponses = surveys.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(surveyResponses);
    }

    @GetMapping("/my-surveys")
    public ResponseEntity<List<SurveyResponse>> getMySurveys() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Survey> surveys = surveyService.getSurveysByCreator(user);
        List<SurveyResponse> surveyResponses = surveys.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(surveyResponses);
    }

    @PostMapping
    public ResponseEntity<SurveyResponse> createSurvey(@RequestBody SurveyCreateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Survey survey = surveyService.createSurvey(request, user);
        return ResponseEntity.ok(convertToResponse(survey));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponse> getSurvey(@PathVariable Long id) {
        Survey survey = surveyService.getSurveyById(id);
        return ResponseEntity.ok(convertToResponse(survey));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<String> submitResponse(@PathVariable Long id, @RequestBody ResponseSubmitRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        surveyService.submitResponse(id, request, user);
        return ResponseEntity.ok("Response submitted successfully!");
    }

    @GetMapping("/{id}/analytics")
    public ResponseEntity<SurveyAnalytics> getSurveyAnalytics(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        SurveyAnalytics analytics = surveyService.getSurveyAnalytics(id, user);
        return ResponseEntity.ok(analytics);
    }

    private SurveyResponse convertToResponse(Survey survey) {
        SurveyResponse response = new SurveyResponse();
        response.setId(survey.getId());
        response.setTitle(survey.getTitle());
        response.setDescription(survey.getDescription());
        response.setCreatorUsername(survey.getCreator().getUsername());
        response.setCreatedAt(survey.getCreatedAt());
        response.setQuestions(survey.getQuestions());
        return response;
    }
}
