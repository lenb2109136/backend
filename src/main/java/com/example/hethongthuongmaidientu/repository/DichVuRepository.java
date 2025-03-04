package com.example.hethongthuongmaidientu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.DichVu;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer>{

}
