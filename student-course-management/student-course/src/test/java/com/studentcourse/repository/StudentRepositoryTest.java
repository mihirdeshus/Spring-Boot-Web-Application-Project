package com.studentcourse.repository;

import com.studentcourse.dto.StudentCourseDTO;
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
class StudentRepositoryTest {

    @Autowired StudentRepository studentRepository;
    @Autowired CourseRepository  courseRepository;

    private Course dbms;
    private Course os;
    private Student mihir;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        dbms  = courseRepository.save(new Course("Database Management", "Prof. Gupta"));
        os    = courseRepository.save(new Course("Operating Systems",   "Prof. Verma"));

        mihir = studentRepository.save(new Student("Mihir",   "mihir@gmail.com",   dbms));
                studentRepository.save(new Student("Ishan",   "ishan@gmail.com",   os));
                studentRepository.save(new Student("Hrigved", "hrigved@gmail.com", dbms));
    }

    @Test
    @DisplayName("findAll returns all students")
    void findAll_returnsAll() {
        assertThat(studentRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("findByEmailIgnoreCase returns correct student")
    void findByEmail_returnsStudent() {
        Optional<Student> found = studentRepository.findByEmailIgnoreCase("MIHIR@GMAIL.COM");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Mihir");
    }

    @Test
    @DisplayName("findByCourseId returns only students in that course")
    void findByCourseId_correctStudents() {
        List<Student> dbmsStudents = studentRepository.findByCourseId(dbms.getId());
        assertThat(dbmsStudents).hasSize(2);
    }

    @Test
    @DisplayName("findByNameContainingIgnoreCase does partial match")
    void findByName_partialMatch() {
        List<Student> results = studentRepository.findByNameContainingIgnoreCase("ih");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Mihir");
    }

    @Test
    @DisplayName("fetchStudentCourseDetails INNER JOIN returns all rows")
    void innerJoinQuery_returnsAllRows() {
        List<StudentCourseDTO> data = studentRepository.fetchStudentCourseDetails();
        assertThat(data).hasSize(3);
        assertThat(data).extracting(StudentCourseDTO::getStudentName)
                        .contains("Mihir", "Ishan", "Hrigved");
        assertThat(data).extracting(StudentCourseDTO::getCourseTitle)
                        .contains("Database Management", "Operating Systems");
    }

    @Test
    @DisplayName("existsByEmailAndIdNot detects duplicate email")
    void existsByEmailAndIdNot_detectsDuplicate() {
        boolean duplicate = studentRepository.existsByEmailAndIdNot("mihir@gmail.com", 999L);
        assertThat(duplicate).isTrue();
    }

    @Test
    @DisplayName("existsByEmailAndIdNot allows same student to keep their email")
    void existsByEmailAndIdNot_allowsSameStudent() {
        boolean duplicate = studentRepository.existsByEmailAndIdNot("mihir@gmail.com", mihir.getId());
        assertThat(duplicate).isFalse();
    }

    @Test
    @DisplayName("save persists a new student")
    void save_persistsNewStudent() {
        Student nirmit = studentRepository.save(new Student("Nirmit", "nirmit@gmail.com", os));
        assertThat(nirmit.getId()).isNotNull();
        assertThat(studentRepository.findAll()).hasSize(4);
    }
}
