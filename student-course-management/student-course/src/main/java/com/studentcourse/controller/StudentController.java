package com.studentcourse.controller;

import com.studentcourse.entity.Student;
import com.studentcourse.exception.DuplicateEmailException;
import com.studentcourse.exception.ResourceNotFoundException;
import com.studentcourse.service.CourseService;
import com.studentcourse.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // ── LIST ──────────────────────────────────────────────
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("pageTitle", "All Students");
        return "students/list";
    }

    // ── JOIN VIEW ─────────────────────────────────────────
    @GetMapping("/join")
    public String joinView(Model model) {
        model.addAttribute("data", studentService.getStudentCourseDetails());
        model.addAttribute("pageTitle", "Student–Course Enrollment");
        return "students/join";
    }

    // ── ADD FORM ──────────────────────────────────────────
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("pageTitle", "Add New Student");
        return "students/form";
    }

    // ── SAVE (CREATE) ─────────────────────────────────────
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              @RequestParam("courseId") Long courseId,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("pageTitle", "Add New Student");
            return "students/form";
        }
        try {
            student.setCourse(courseService.findById(courseId));
            studentService.save(student);
            redirectAttributes.addFlashAttribute("successMsg",
                    "Student '" + student.getName() + "' added successfully!");
        } catch (DuplicateEmailException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("pageTitle", "Add New Student");
            return "students/form";
        }
        return "redirect:/students";
    }

    // ── EDIT FORM ─────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("student", studentService.findById(id));
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("pageTitle", "Edit Student");
            return "students/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/students";
        }
    }

    // ── UPDATE ────────────────────────────────────────────
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") Student student,
                                BindingResult result,
                                @RequestParam("courseId") Long courseId,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("pageTitle", "Edit Student");
            return "students/form";
        }
        try {
            studentService.update(id, student, courseId);
            redirectAttributes.addFlashAttribute("successMsg", "Student updated successfully!");
        } catch (DuplicateEmailException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("pageTitle", "Edit Student");
            return "students/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Update failed: " + e.getMessage());
        }
        return "redirect:/students";
    }
}
