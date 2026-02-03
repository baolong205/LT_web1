package com.example.studentmaneger.controller;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý giao diện người dùng (Thymeleaf)
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 1. Trang danh sách sinh viên
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students"; 
    }

    // 2. Trang thêm/sửa (Giao diện)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "addStudent"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không hợp lệ: " + id));
        model.addAttribute("student", student);
        return "addStudent"; 
    }
}

/**
 * PHẦN SỬA ĐỔI: API Controller xử lý dữ liệu JSON (Fetch API)
 * Đường dẫn gốc: http://localhost:8080/api/students
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin("*") // Cho phép gọi từ trình duyệt mà không bị lỗi CORS
class StudentRestController {

    @Autowired
    private StudentService studentService;

    // Lấy toàn bộ JSON: GET /api/students
    @GetMapping
    public List<Student> getAllApi() {
        return studentService.getAllStudents();
    }

    // Tìm kiếm JSON: GET /api/students/search?keyword=...
    @GetMapping("/search")
    public List<Student> searchApi(@RequestParam("keyword") String keyword) {
        return studentService.searchStudents(keyword);
    }

    // Lưu/Cập nhật JSON: POST /api/students/save
    @PostMapping("/save")
    public Student saveApi(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    // Xóa JSON: DELETE /api/students/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApi(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Đã xóa sinh viên ID: " + id);
    }
}