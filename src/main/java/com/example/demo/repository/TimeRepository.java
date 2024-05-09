package com.example.demo.repository;

import com.example.demo.entity.TrackTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRepository extends JpaRepository<TrackTimeEntity, Long> {

    List<TrackTimeEntity> findByMethodNameStartingWith(String prefix);
}
