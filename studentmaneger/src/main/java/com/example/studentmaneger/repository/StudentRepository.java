package com.example.studentmaneger.repository;

import com.example.studentmaneger.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    // Thêm dòng này để Spring hiểu cách tìm kiếm theo tên
    List<Student> findByNameContainingIgnoreCase(String name);
}