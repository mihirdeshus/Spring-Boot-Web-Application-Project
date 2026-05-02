package com.studentcourse.dto;

/**
 * DTO returned by the JPQL INNER JOIN query between Student and Course.
 */
public class StudentCourseDTO {

    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long courseId;
    private String courseTitle;
    private String instructor;

    public StudentCourseDTO(Long studentId, String studentName, String studentEmail,
                             Long courseId, String courseTitle, String instructor) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.instructor = instructor;
    }

    public Long getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getStudentEmail() { return studentEmail; }
    public Long getCourseId() { return courseId; }
    public String getCourseTitle() { return courseTitle; }
    public String getInstructor() { return instructor; }
}
