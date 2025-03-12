package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.NHANVIEN;
import com.example.hethongthuongmaidientu.repository.NhanVienRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NhanVienService {
	@Autowired
	private NhanVienRepository nhanVienRepository;
	
	public NHANVIEN findBySoDienThoai(String sdt) {
		return nhanVienRepository.findBySoDienThoai(sdt);
	}
}
