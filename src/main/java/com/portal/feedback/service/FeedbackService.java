package com.portal.feedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.portal.feedback.dto.FeedbackRequest;
import com.portal.feedback.dto.FeedbackResponse;
import com.portal.feedback.entity.Feedback;
import com.portal.feedback.exception.ResourceNotFoundException;
import com.portal.feedback.repository.FeedbackRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${enrollment.service.url}")
    private String enrollmentServiceUrl; // http://localhost:8081

    public Feedback createFeedback(FeedbackRequest request) {
        if (!isUserEnrolled(request.getUserId(), request.getCourseId())) {
            throw new RuntimeException("User is not enrolled in this course");
        }

        Feedback feedback = new Feedback();
        feedback.setUserId(request.getUserId());
        feedback.setCourseId(request.getCourseId());
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());

        return feedbackRepository.save(feedback);
    }

    public List<FeedbackResponse> getFeedbackByCourseId(String courseId) {
        List<Feedback> feedbacks = feedbackRepository.findByCourseId(courseId);
        return feedbacks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + id));
        feedbackRepository.delete(feedback);
    }

    private FeedbackResponse mapToResponse(Feedback feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setUserId(feedback.getUserId());
        response.setRating(feedback.getRating());
        response.setComment(feedback.getComment());
        response.setCreatedAt(feedback.getCreatedAt());
        return response;
    }

    private boolean isUserEnrolled(String userId, String courseId) {
    	
    	return true;
    }
	/*
	 * private boolean isUserEnrolled(String userId, String courseId) { try { String
	 * url = enrollmentServiceUrl + "/enrollments/user/" + userId;
	 * 
	 * ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
	 * url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {} );
	 * 
	 * if (response.getStatusCode().is2xxSuccessful()) { List<Map<String, Object>>
	 * enrollments = response.getBody(); if (enrollments != null) { for (Map<String,
	 * Object> enrollment : enrollments) { if
	 * (courseId.equals(enrollment.get("course_id"))) { return true; } } } }
	 * 
	 * } catch (Exception e) {
	 * System.out.println("Error calling Enrollment Service: " + e.getMessage()); }
	 * 
	 * return false; }
	 */
}
