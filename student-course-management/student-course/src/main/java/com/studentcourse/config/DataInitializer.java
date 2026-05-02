package com.studentcourse.config;

import com.studentcourse.entity.Course;
import com.studentcourse.entity.Student;
import com.studentcourse.repository.CourseRepository;
import com.studentcourse.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;

    public DataInitializer(CourseRepository courseRepo, StudentRepository studentRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public void run(String... args) {

        // ── 10 Courses ────────────────────────────────────
        Course c1  = new Course("Data Structures",       "Prof. Sharma");
        Course c2  = new Course("Operating Systems",     "Prof. Verma");
        Course c3  = new Course("Database Management",   "Prof. Gupta");
        Course c4  = new Course("Computer Networks",     "Prof. Singh");
        Course c5  = new Course("Machine Learning",      "Prof. Patel");
        Course c6  = new Course("Web Development",       "Prof. Mehta");
        Course c7  = new Course("Cloud Computing",       "Prof. Joshi");
        Course c8  = new Course("Cyber Security",        "Prof. Rao");
        Course c9  = new Course("Artificial Intelligence","Prof. Khan");
        Course c10 = new Course("Software Engineering",  "Prof. Iyer");

        List<Course> courses = courseRepo.saveAll(
                List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));

        // ── 10 Students (friends' names) ──────────────────
        studentRepo.saveAll(List.of(
            new Student("Mihir",    "mihir@gmail.com",    courses.get(0)),
            new Student("Ishan",    "ishan@gmail.com",    courses.get(1)),
            new Student("Hrigved",  "hrigved@gmail.com",  courses.get(2)),
            new Student("Nirmit",   "nirmit@gmail.com",   courses.get(3)),
            new Student("Akansha",  "akansha@gmail.com",  courses.get(4)),
            new Student("Bhoomi",   "bhoomi@gmail.com",   courses.get(5)),
            new Student("Palak",    "palak@gmail.com",    courses.get(0)),
            new Student("Nilay",    "nilay@gmail.com",    courses.get(1)),
            new Student("Aditya",   "aditya@gmail.com",   courses.get(6)),
            new Student("Riya",     "riya@gmail.com",     courses.get(7))
        ));

        System.out.println("✅  Sample data loaded: 10 courses and 10 students.");
    }
}
