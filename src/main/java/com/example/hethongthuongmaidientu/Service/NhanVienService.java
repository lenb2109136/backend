package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.repository.NhanVienRepository;

@Service
public class NhanVienService {
	@Autowired
	private NhanVienRepository nhanVienRepository;
}
