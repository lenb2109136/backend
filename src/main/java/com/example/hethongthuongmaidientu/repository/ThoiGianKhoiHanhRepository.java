package com.example.hethongthuongmaidientu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;

@Repository
public interface ThoiGianKhoiHanhRepository extends JpaRepository<ThoiGianKhoiHanh, Integer>{

}
