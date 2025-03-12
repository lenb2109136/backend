package com.example.hethongthuongmaidientu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.NHANVIEN;

@Repository
public interface NhanVienRepository extends JpaRepository<NHANVIEN, Integer>{
	NHANVIEN findBySoDienThoai(String soDienThoai);
}
