package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.repository.ThoiGianKhoiHanhRepository;

@Service
public class ThoiGianKhoiHanhService {
	@Autowired
	private ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepository;
}
