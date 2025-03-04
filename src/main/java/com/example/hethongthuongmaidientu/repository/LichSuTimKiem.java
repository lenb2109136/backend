package com.example.hethongthuongmaidientu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.LICHSUTIMKIEM;

@Repository
public interface LichSuTimKiem extends JpaRepository<LICHSUTIMKIEM, Integer> {

}
