package com.portal.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portal.feedback.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCourseId(String courseId);
}