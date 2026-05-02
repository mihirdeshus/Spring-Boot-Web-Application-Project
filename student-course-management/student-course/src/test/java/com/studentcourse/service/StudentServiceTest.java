package com.studentcourse.service;

import com.studentcourse.dto.StudentCourseDTO;
import com.studentcourse.entity.Course;
import com.studentcourse.entity.Student;
import com.studentcourse.exception.DuplicateEmailException;
import com.studentcourse.exception.ResourceNotFoundException;
import com.studentcourse.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock StudentRepository studentRepository;
    @Mock CourseService     courseService;
    @InjectMocks StudentService studentService;

    private Course course;
    private Student student;

    @BeforeEach
    void setUp() {
        course = new Course("Database Management", "Prof. Gupta");
        course.setId(1L);

        student = new Student("Mihir", "mihir@gmail.com", course);
        student.setId(10L);
    }

    @Test
    @DisplayName("findAll returns all students")
    void findAll_returnsAll() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        assertThat(studentService.findAll()).hasSize(1);
        verify(studentRepository).findAll();
    }

    @Test
    @DisplayName("findById returns student when found")
    void findById_returnsStudent() {
        when(studentRepository.findById(10L)).thenReturn(Optional.of(student));
        Student result = studentService.findById(10L);
        assertThat(result.getName()).isEqualTo("Mihir");
    }

    @Test
    @DisplayName("findById throws ResourceNotFoundException when not found")
    void findById_throwsWhenNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> studentService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("save succeeds when email is unique")
    void save_uniqueEmail_succeeds() {
        when(studentRepository.findByEmailIgnoreCase("mihir@gmail.com")).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.save(student);
        assertThat(result.getName()).isEqualTo("Mihir");
        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("save throws DuplicateEmailException for duplicate email")
    void save_duplicateEmail_throwsException() {
        Student other = new Student("Other", "mihir@gmail.com", course);
        other.setId(99L);
        when(studentRepository.findByEmailIgnoreCase("mihir@gmail.com"))
                .thenReturn(Optional.of(other));

        // new student (id=null) with same email
        Student newStudent = new Student("Palak", "mihir@gmail.com", course);
        assertThatThrownBy(() -> studentService.save(newStudent))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessageContaining("mihir@gmail.com");
    }

    @Test
    @DisplayName("getStudentCourseDetails delegates to INNER JOIN query")
    void getStudentCourseDetails_delegatesToQuery() {
        StudentCourseDTO dto = new StudentCourseDTO(
                10L, "Mihir", "mihir@gmail.com", 1L, "Database Management", "Prof. Gupta");
        when(studentRepository.fetchStudentCourseDetails()).thenReturn(List.of(dto));

        List<StudentCourseDTO> result = studentService.getStudentCourseDetails();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourseTitle()).isEqualTo("Database Management");
        verify(studentRepository).fetchStudentCourseDetails();
    }

    @Test
    @DisplayName("update modifies student and links correct course")
    void update_modifiesStudent() {
        when(studentRepository.findById(10L)).thenReturn(Optional.of(student));
        when(studentRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(courseService.findById(1L)).thenReturn(course);
        when(studentRepository.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));

        Student updated = new Student("Mihir Patil", "mihir.patil@gmail.com", course);
        Student result  = studentService.update(10L, updated, 1L);

        assertThat(result.getName()).isEqualTo("Mihir Patil");
        assertThat(result.getEmail()).isEqualTo("mihir.patil@gmail.com");
    }

    @Test
    @DisplayName("update throws DuplicateEmailException when email already taken")
    void update_duplicateEmail_throws() {
        when(studentRepository.findById(10L)).thenReturn(Optional.of(student));
        when(studentRepository.existsByEmailAndIdNot("taken@gmail.com", 10L)).thenReturn(true);

        Student updated = new Student("Mihir", "taken@gmail.com", course);
        assertThatThrownBy(() -> studentService.update(10L, updated, 1L))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("delete calls repository.delete")
    void delete_callsRepository() {
        when(studentRepository.findById(10L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);
        studentService.delete(10L);
        verify(studentRepository).delete(student);
    }
}
