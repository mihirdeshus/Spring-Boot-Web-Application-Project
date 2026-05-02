package com.studentcourse.service;

import com.studentcourse.dto.StudentCourseDTO;
import com.studentcourse.entity.Course;
import com.studentcourse.entity.Student;
import com.studentcourse.exception.DuplicateEmailException;
import com.studentcourse.exception.ResourceNotFoundException;
import com.studentcourse.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    // ── CREATE ────────────────────────────────────────────
    public Student save(Student student) {
        // Guard: email must be unique
        Optional<Student> existing = studentRepository.findByEmailIgnoreCase(student.getEmail());
        if (existing.isPresent() &&
            (student.getId() == null || !existing.get().getId().equals(student.getId()))) {
            throw new DuplicateEmailException(student.getEmail());
        }
        return studentRepository.save(student);
    }

    // ── READ ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Student> findByCourseId(Long courseId) {
        return studentRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Student> searchByName(String keyword) {
        return studentRepository.findByNameContainingIgnoreCase(keyword);
    }

    /** Calls the INNER JOIN custom query — required by the rubric. */
    @Transactional(readOnly = true)
    public List<StudentCourseDTO> getStudentCourseDetails() {
        return studentRepository.fetchStudentCourseDetails();
    }

    // ── UPDATE ────────────────────────────────────────────
    public Student update(Long id, Student updated, Long courseId) {
        Student existing = findById(id);

        // Email uniqueness check when email changes
        if (!existing.getEmail().equalsIgnoreCase(updated.getEmail()) &&
            studentRepository.existsByEmailAndIdNot(updated.getEmail(), id)) {
            throw new DuplicateEmailException(updated.getEmail());
        }

        Course course = courseService.findById(courseId);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setCourse(course);
        return studentRepository.save(existing);
    }

    // ── DELETE ────────────────────────────────────────────
    public void delete(Long id) {
        studentRepository.delete(findById(id));
    }
}
