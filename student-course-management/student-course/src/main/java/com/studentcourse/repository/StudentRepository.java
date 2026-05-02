package com.studentcourse.repository;

import com.studentcourse.dto.StudentCourseDTO;
import com.studentcourse.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmailIgnoreCase(String email);

    List<Student> findByCourseId(Long courseId);

    List<Student> findByNameContainingIgnoreCase(String keyword);

    /**
     * INNER JOIN between Student and Course — returns typed DTO.
     * This is the required custom join query.
     */
    @Query("SELECT new com.studentcourse.dto.StudentCourseDTO(" +
           "s.id, s.name, s.email, c.id, c.title, c.instructor) " +
           "FROM Student s INNER JOIN s.course c " +
           "ORDER BY c.title, s.name")
    List<StudentCourseDTO> fetchStudentCourseDetails();

    /** Check if email is taken by a different student (used during update). */
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.email = :email AND s.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
}
