package com.studentcourse.service;

import com.studentcourse.entity.Course;
import com.studentcourse.exception.ResourceNotFoundException;
import com.studentcourse.repository.CourseRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock  CourseRepository courseRepository;
    @InjectMocks CourseService courseService;

    private Course sampleCourse;

    @BeforeEach
    void setUp() {
        sampleCourse = new Course("Database Management", "Prof. Gupta");
        sampleCourse.setId(1L);
    }

    @Test
    @DisplayName("findAll delegates to repository")
    void findAll_delegatesToRepository() {
        when(courseRepository.findAll()).thenReturn(List.of(sampleCourse));
        assertThat(courseService.findAll()).hasSize(1);
        verify(courseRepository).findAll();
    }

    @Test
    @DisplayName("findById returns course when found")
    void findById_returnsCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));
        Course result = courseService.findById(1L);
        assertThat(result.getTitle()).isEqualTo("Database Management");
    }

    @Test
    @DisplayName("findById throws ResourceNotFoundException when not found")
    void findById_throwsWhenNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> courseService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("save calls repository.save")
    void save_callsRepository() {
        when(courseRepository.save(any(Course.class))).thenReturn(sampleCourse);
        Course result = courseService.save(sampleCourse);
        assertThat(result.getTitle()).isEqualTo("Database Management");
        verify(courseRepository).save(sampleCourse);
    }

    @Test
    @DisplayName("update modifies course fields")
    void update_modifiesFields() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));
        when(courseRepository.save(any(Course.class))).thenAnswer(inv -> inv.getArgument(0));

        Course updated = new Course("DBMS Advanced", "Prof. Gupta");
        Course result  = courseService.update(1L, updated);

        assertThat(result.getTitle()).isEqualTo("DBMS Advanced");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    @DisplayName("update throws when course not found")
    void update_throwsWhenNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> courseService.update(99L, sampleCourse))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("delete calls repository.delete")
    void delete_callsRepository() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));
        doNothing().when(courseRepository).delete(sampleCourse);
        courseService.delete(1L);
        verify(courseRepository).delete(sampleCourse);
    }
}
