package com.example.hethongthuongmaidientu.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.DichVu;
import com.example.hethongthuongmaidientu.repository.DichVuRepository;

@Service
public class DichVuService {
	@Autowired
	private DichVuRepository dichVuRepository;
	
	public List<DichVu> getDichVuPhuHop() {
		return dichVuRepository.findAll();
	}
	
	public List<DichVu> getAll() {
		return dichVuRepository.findAll();
	}
}
