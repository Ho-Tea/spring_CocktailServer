package com.example.demo.persistence;


import com.example.demo.model.TasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasteRepository extends JpaRepository<TasteEntity, String> {
    List<TasteEntity> findByUserId(String userId);
}
