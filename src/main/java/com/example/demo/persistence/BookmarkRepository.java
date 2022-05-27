package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.BookmarkEntity;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, String>{
	List<BookmarkEntity> findByUserId(String userId);
}
