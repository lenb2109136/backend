package com.example.hethongthuongmaidientu.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.PhanHoi;
import com.example.hethongthuongmaidientu.repository.PhanHoiRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PhanHoiService {
	@Autowired
	private PhanHoiRepository phanHoiRepository;
	public List<PhanHoi> getPhanHoiByTour(int idtour){
		return phanHoiRepository.getList(idtour);
	}
	public List<PhanHoi> getPhanHoiByPhanHoi(int ph){
		return phanHoiRepository.getListByTour(ph);
	}
	public PhanHoi getById(int id) {
		return phanHoiRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Không tìm thấy phản hồi cần tìm"));
	}
	
	@Transactional
	public void savePhanHoi(PhanHoi p) {
		phanHoiRepository.save(p);
	}
}

