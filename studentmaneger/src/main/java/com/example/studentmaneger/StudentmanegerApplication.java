package com.example.studentmaneger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class StudentmanegerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentmanegerApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello Spring Boot API";
	}

	@GetMapping("/greet")
	public String greet(@RequestParam String name) {
		return "Xin Chào " + name;
	}

	@GetMapping("/students/search")
	public String search(
			@RequestParam String keyword,
			@RequestParam(defaultValue = "1") int page) {
		return "keyword: " + keyword + ", page: " + page;
	}

	@GetMapping("/students/{id}")
	public String getStudent(@PathVariable int id) {
		return "Sinh viên có mã: " + id;
	}
	@GetMapping("/student	")
	public Student getStudent() {
    	return new Student(1, "Nguyễn Văn A", 20);
}
	@GetMapping("/students")
	public List<Student> getStudents() {
		 List<Student> list = new ArrayList<>();
   		 list.add(new Student(1, "Hoàng Ngọc ", 20));
   		 list.add(new Student(2, "Bảo Long", 20));
   		 return list;
	}
}

class Student {
	private int id;
	private String name;
	private int age;

	public Student(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}