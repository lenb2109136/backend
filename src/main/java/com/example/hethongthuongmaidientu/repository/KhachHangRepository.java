package com.example.hethongthuongmaidientu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.CHAN;
import com.example.hethongthuongmaidientu.model.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>{

}
