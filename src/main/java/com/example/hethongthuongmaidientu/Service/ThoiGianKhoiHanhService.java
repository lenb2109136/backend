package com.example.hethongthuongmaidientu.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.repository.ThoiGianKhoiHanhRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ThoiGianKhoiHanhService {
	@Autowired
	private ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepository;
	
	//không trùng -> return true
	public boolean kiemtratrung(int idtg,int idkh) {
		ThoiGianKhoiHanh t= thoiGianKhoiHanhRepository.findById(idtg).orElseThrow(()-> new EntityNotFoundException("Vui lòng chọn thời gian khởi hành phù hợp"));
		List<ThoiGianKhoiHanh> tt= thoiGianKhoiHanhRepository.gettrung(t.getId(),idkh);
		if(tt.size()==0) {
			return true;
		}
		else {
			for(int i=0;i<tt.size();i++) {
				if(tt.get(i).getId()==t.getId()||
			(t.getThoiGian().isAfter(tt.get(i).getThoiGian())&&
					t.getThoiGian().isBefore(tt.get(i).getThoiGian().plusDays(t.getTour().getSoNgay()))	)) {
					return false;
				}
			}
		}
		return true;
	}
}
