package com.studentcourse.repository;

import com.studentcourse.entity.Course;
import com.studentcourse.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired CourseRepository courseRepository;
    @Autowired StudentRepository studentRepository;

    private Course dbms;
    private Course os;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        dbms = courseRepository.save(new Course("Database Management", "Prof. Gupta"));
        os   = courseRepository.save(new Course("Operating Systems",   "Prof. Verma"));
        courseRepository.save(new Course("Machine Learning", "Prof. Patel"));
    }

    @Test
    @DisplayName("findAll returns all saved courses")
    void findAll_returnsAllCourses() {
        assertThat(courseRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("findById returns correct course")
    void findById_returnsCorrectCourse() {
        Optional<Course> found = courseRepository.findById(dbms.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Database Management");
    }

    @Test
    @DisplayName("findByTitleIgnoreCase is case-insensitive")
    void findByTitle_caseInsensitive() {
        Optional<Course> found = courseRepository.findByTitleIgnoreCase("operating systems");
        assertThat(found).isPresent();
        assertThat(found.get().getInstructor()).isEqualTo("Prof. Verma");
    }

    @Test
    @DisplayName("findByInstructorContainingIgnoreCase does partial match")
    void findByInstructor_partialMatch() {
        List<Course> results = courseRepository.findByInstructorContainingIgnoreCase("gupta");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Database Management");
    }

    @Test
    @DisplayName("findCoursesWithStudents returns only courses that have students")
    void findCoursesWithStudents_onlyCoursesWithStudents() {
        studentRepository.save(new Student("Mihir", "mihir@gmail.com", dbms));

        List<Course> withStudents = courseRepository.findCoursesWithStudents();
        assertThat(withStudents).hasSize(1);
        assertThat(withStudents.get(0).getTitle()).isEqualTo("Database Management");
    }

    @Test
    @DisplayName("countStudentsByCourseId returns correct count")
    void countStudentsByCourseId_correctCount() {
        studentRepository.save(new Student("Ishan",   "ishan@gmail.com",   dbms));
        studentRepository.save(new Student("Hrigved", "hrigved@gmail.com", dbms));

        long count = courseRepository.countStudentsByCourseId(dbms.getId());
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("save persists a new course")
    void save_persistsNewCourse() {
        Course saved = courseRepository.save(new Course("Cloud Computing", "Prof. Joshi"));
        assertThat(saved.getId()).isNotNull();
        assertThat(courseRepository.findAll()).hasSize(4);
    }

    @Test
    @DisplayName("delete removes a course")
    void delete_removesCourse() {
        courseRepository.delete(os);
        assertThat(courseRepository.findById(os.getId())).isEmpty();
        assertThat(courseRepository.findAll()).hasSize(2);
    }
}
