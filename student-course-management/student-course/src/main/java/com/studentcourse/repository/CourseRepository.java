package com.studentcourse.repository;

import com.studentcourse.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByTitleIgnoreCase(String title);

    List<Course> findByInstructorContainingIgnoreCase(String keyword);

    /** Courses that have at least one enrolled student. */
    @Query("SELECT DISTINCT c FROM Course c JOIN c.students s")
    List<Course> findCoursesWithStudents();

    /** Count of students in a given course. */
    @Query("SELECT COUNT(s) FROM Student s WHERE s.course.id = :courseId")
    long countStudentsByCourseId(@Param("courseId") Long courseId);
}
