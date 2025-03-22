package com.example.hethongthuongmaidientu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hethongthuongmaidientu.model.LoaiTour;

public interface LoaiTourRepository extends JpaRepository<LoaiTour, Integer> {
    @Query("SELECT MAX(lt.id) FROM LoaiTour lt")
    Integer findMaxId();
}