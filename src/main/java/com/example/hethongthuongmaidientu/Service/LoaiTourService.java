package com.example.hethongthuongmaidientu.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.LoaiTour;
import com.example.hethongthuongmaidientu.repository.LoaiTourRepository;

@Service
public class LoaiTourService {
	@Autowired
	private LoaiTourRepository loaiTourRepository;
	
	public List<LoaiTour> getLoaiTour(){
		return loaiTourRepository.findAll();
	}
}
