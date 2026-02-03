package com.example.studentmaneger.service;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Thêm cái này
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(int id) {
        return repository.findById(id);
    }

    @Transactional // Đảm bảo dữ liệu được ghi xuống DB ngay lập tức
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    // SỬA LẠI HÀM XÓA:
    @Transactional
    public void deleteStudent(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            repository.flush(); // Ép buộc SQL Server thực hiện lệnh xóa ngay bây giờ
        } else {
            throw new RuntimeException("Không tìm thấy sinh viên ID: " + id);
        }
    }

    public List<Student> searchStudents(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}