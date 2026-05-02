package com.studentcourse.controller;

import com.studentcourse.entity.Course;
import com.studentcourse.exception.ResourceNotFoundException;
import com.studentcourse.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("pageTitle", "All Courses");
        return "courses/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Add New Course");
        return "courses/form";
    }

    @PostMapping("/save")
    public String saveCourse(@Valid @ModelAttribute("course") Course course,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Course");
            return "courses/form";
        }
        try {
            courseService.save(course);
            redirectAttributes.addFlashAttribute("successMsg",
                    "Course '" + course.getTitle() + "' saved successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Error saving course: " + e.getMessage());
            model.addAttribute("pageTitle", "Add New Course");
            return "courses/form";
        }
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("course", courseService.findById(id));
            model.addAttribute("pageTitle", "Edit Course");
            return "courses/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/courses";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("course") Course course,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Course");
            return "courses/form";
        }
        try {
            courseService.update(id, course);
            redirectAttributes.addFlashAttribute("successMsg", "Course updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Update failed: " + e.getMessage());
        }
        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("course", courseService.findById(id));
            model.addAttribute("pageTitle", "Course Details");
            return "courses/detail";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/courses";
        }
    }
}
