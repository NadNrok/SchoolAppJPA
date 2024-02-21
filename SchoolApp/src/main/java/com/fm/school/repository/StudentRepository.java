package com.fm.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fm.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT COUNT(s) FROM Student s")
    Long getCount();

    @Query("SELECT s.studentId FROM Student s")
    List<Integer> getStudentIds();
}
