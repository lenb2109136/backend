package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.repository.KhachHangRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class KhachHangService {
	@Autowired
	private KhachHangRepository khachHangRepository;
	
	public KhachHang findBySoDienThoai(String sdt) {
		return khachHangRepository.findBySoDienThoai(sdt);
				}
}
