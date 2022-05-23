package com.example.SoftwareProject.persistence;

import com.example.SoftwareProject.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserId(String UserId);  //새 Todo list 반환
}
