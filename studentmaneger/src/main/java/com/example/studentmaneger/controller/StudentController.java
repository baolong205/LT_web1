package com.example.studentmaneger.controller;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 1. ĐỌC: Hiển thị danh sách sinh viên
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students"; 
    }

    // 2. HIỂN THỊ FORM THÊM MỚI
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "redirect:/students";
        
    }

    // 3. XỬ LÝ LƯU (Dùng chung cho cả Thêm và Cập nhật)
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        // JpaRepository tự hiểu: Nếu ID mới -> Insert, nếu ID đã có -> Update
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // 4. HIỂN THỊ FORM CẬP NHẬT (Lấy dữ liệu cũ đổ vào Form)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không hợp lệ: " + id));
        
        model.addAttribute("student", student);
        return "addStudent"; // Dùng chung giao diện với trang thêm
    }

    // 5. XÓA: Xử lý xóa theo ID
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}