package com.example.studentmaneger.controller;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ======================================================
    // PHẦN 1: CÁC ĐƯỜNG DẪN GIAO DIỆN (VIEW)
    // ======================================================

    // 1. Trang danh sách sinh viên: http://localhost:8080/students
    @GetMapping("/students")
    public String listView(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    // 2. Trang thêm sinh viên: http://localhost:8080/students/add
    @GetMapping("/students/add")
    public String addView(Model model) {
        model.addAttribute("student", new Student());
        return "addStudent";
    }

    // 3. Trang sửa sinh viên: http://localhost:8080/students/edit/{id}
    @GetMapping("/students/edit/{id}")
    public String editView(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không hợp lệ: " + id));
        model.addAttribute("student", student);
        return "addStudent";
    }

    // ======================================================
    // PHẦN 2: 6 YÊU CẦU API (NỘP BÀI) - GỐC /api/students
    // ======================================================

    // Yêu cầu 5: Lấy danh sách toàn bộ sinh viên (Get All)
    @GetMapping("/api/students")
    @ResponseBody
    public List<Student> getAllApi() {
        return studentService.getAllStudents();
    }

    // Yêu cầu 4: Lấy sinh viên theo ID
    @GetMapping("/api/students/{id}")
    @ResponseBody
    public ResponseEntity<Student> getByIdApi(@PathVariable int id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Yêu cầu 3: Tìm kiếm sinh viên theo tên (keyword)
    @GetMapping("/api/students/search")
    @ResponseBody
    public List<Student> searchApi(@RequestParam("keyword") String keyword) {
        return studentService.searchStudents(keyword);
    }

    // Yêu cầu 1: Thêm sinh viên mới
    @PostMapping("/api/students")
    @ResponseBody
    public Student addApi(@RequestBody Student student) {
        // Ép về null để Hibernate hiểu đây là thêm mới hoàn toàn
        student.setId(null);
        return studentService.saveStudent(student);
    }

    // Yêu cầu 6: Cập nhật thông tin sinh viên
    @PostMapping("/api/students/update/{id}")
    @ResponseBody
    public ResponseEntity<Student> updateApi(@PathVariable int id, @RequestBody Student student) {
        return studentService.getStudentById(id).map(existing -> {
            student.setId(id);
            return ResponseEntity.ok(studentService.saveStudent(student));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Yêu cầu 2: Xóa sinh viên
    @PostMapping("/api/students/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteApi(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Xóa thành công sinh viên ID: " + id);
    }
}