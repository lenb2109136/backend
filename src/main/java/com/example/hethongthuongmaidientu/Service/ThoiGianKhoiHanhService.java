package com.example.hethongthuongmaidientu.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.repository.ThoiGianKhoiHanhRepository;
import com.example.hethongthuongmaidientu.repository.TourRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ThoiGianKhoiHanhService {
	@Autowired
	private ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepository;
	
	@Autowired
	private TourRepository tourRepository;
	
	//không trùng -> return true
	public boolean kiemtratrung(ThoiGianKhoiHanh t,String idkh) {
		List<ThoiGianKhoiHanh> tt= thoiGianKhoiHanhRepository.gettrung(t.getTour().getId(),idkh);
		if(tt.size()==0) {
			System.out.println("vào điều kiện 1");
			return true;
		}
		else {
			for(int i=0;i<tt.size();i++){
				if(tt.get(i).getId()==t.getId()||
					(t.getThoiGian().isAfter(tt.get(i).getThoiGian())&&
					t.getThoiGian().isBefore(tt.get(i).getThoiGian().plusDays(t.getTour().getSoNgay()))	)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean loctour(int idtour, String sdt) {
		Tour t= tourRepository.findById(idtour).orElseThrow(()-> new EntityNotFoundException("Không tìm thấy tour"));
		for(int i=0;i<t.getThoiGianKhoiHanh2().size();i++) {
			if(kiemtratrung(t.getThoiGianKhoiHanh2().get(i), sdt)==true) {
				return true;
			}
		}
		return false;
	}
}
