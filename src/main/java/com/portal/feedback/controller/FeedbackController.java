package com.portal.feedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portal.feedback.dto.FeedbackRequest;
import com.portal.feedback.dto.FeedbackResponse;
import com.portal.feedback.entity.Feedback;
import com.portal.feedback.service.FeedbackService;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestBody FeedbackRequest request) {
        Feedback feedback = feedbackService.createFeedback(request);
        return ResponseEntity.status(201).body(feedback);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<FeedbackResponse>> getFeedbacksByCourse(@PathVariable("courseId") String courseId) {
        List<FeedbackResponse> feedbacks = feedbackService.getFeedbackByCourseId(courseId);
        return ResponseEntity.ok(feedbacks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable("id") Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}
