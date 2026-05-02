package com.studentcourse.service;

import com.studentcourse.entity.Course;
import com.studentcourse.exception.ResourceNotFoundException;
import com.studentcourse.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // ── CREATE ────────────────────────────────────────────
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    // ── READ ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Course> findCoursesWithStudents() {
        return courseRepository.findCoursesWithStudents();
    }

    // ── UPDATE ────────────────────────────────────────────
    public Course update(Long id, Course updated) {
        Course existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setInstructor(updated.getInstructor());
        return courseRepository.save(existing);
    }

    // ── DELETE ────────────────────────────────────────────
    public void delete(Long id) {
        courseRepository.delete(findById(id));
    }
}
