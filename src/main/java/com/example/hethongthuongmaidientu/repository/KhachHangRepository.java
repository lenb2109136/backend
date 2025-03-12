package com.example.hethongthuongmaidientu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.CHAN;
import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.NHANVIEN;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>{
	KhachHang findBySoDienThoai(String soDienThoai);
}
