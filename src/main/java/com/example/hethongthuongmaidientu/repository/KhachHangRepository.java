package com.example.hethongthuongmaidientu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.CHAN;
import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.NHANVIEN;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
	KhachHang findBySoDienThoai(String soDienThoai);

	// List<KhachHang> findBySoDienThoai(String soDienThoai);

	@Query("select p from KhachHang p where p.soDienThoai like %:sdt%")
	List<KhachHang> findBySoDienThoais(@Param("sdt") String soDienThoai);

}
