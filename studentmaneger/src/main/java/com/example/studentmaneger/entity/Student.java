package com.example.studentmaneger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    // THÊM DÒNG NÀY: Để SQL Server tự động sinh ID (1, 2, 3...)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Chuyển từ int sang Integer để hỗ trợ giá trị null khi thêm mới
    
    private String name;
    private int age;
    private String email;

    // Getter và Setter cho ID dùng Integer
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // ... Các Getter và Setter khác giữ nguyên ...
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public int getAge() {
        return age; 
    }
    public void setAge(int age) {
        this.age = age; 
    }
    public String getEmail() {
        return email; 
    }
    public void setEmail(String email) {
        this.email = email; 
    }
}