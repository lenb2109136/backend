package com.example.hethongthuongmaidientu.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.repository.TourRepository;

import DTO.TourDTO;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TourService {
	@Autowired
	private TourRepository tourRepository;

	public List<Map<Object, Object>> getHomeTour() {
		return tourRepository.getHomeTour();
	}

	public List<Map<Object, Object>> getListTour() {
		return tourRepository.getListTour();
	}

	public List<Map<Object, Object>> getListTour(int lt) {
		return tourRepository.getListTour(lt);
	}

	public Tour getInforTour(int id) {
		return tourRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tpur cần tìm"));
	}

	public List<Map<Object, Object>> getByFilter(Map<String, Object> map) {
		String ten=(String)map.get("ten");
		if(ten==null||ten=="") {
			ten="";
		}
		List<Map<Object, Object>> l = tourRepository.getListTourFilter((Integer) map.get("loai"),ten);
		List<Map<Object, Object>> l2 = new ArrayList<Map<Object, Object>>();
		List<Map<Object, Object>> ngay = (List<Map<Object, Object>>) map.get("dsNgay");
		List<Map<Object, Object>> ThoiLuong = (List<Map<Object, Object>>) map.get("thoiLuong");
		float a = ((Number) map.get("giaBatDau")).floatValue();
		float b = ((Number) map.get("giaKetThuc")).floatValue();
		for (int i = 0; i < l.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			LocalDateTime localDateTime = LocalDateTime.parse((String) l.get(i).get("TGKH_THOIGIAN"), formatter);
			int d1 = localDateTime.getDayOfMonth();
			int songay = (int) l.get(i).get("T_SONGAY");
			float gia = ((Number) l.get(i).get("gia")).floatValue();
			boolean kiemtra = false;
		
			if (gia >= a && gia <= b) {
				if (ngay.size() == 0 && ThoiLuong.size() == 0) {
					l2.add(l.get(i));
				}
				else if(ngay.size() == 0) {
					for (int k = 0; k < ThoiLuong.size(); k++) { 
						if (songay >= (Integer) ThoiLuong.get(k).get("batDau")
								&& songay <= (Integer) ThoiLuong.get(k).get("KetThuc")) {
									l2.add(l.get(i));
						} else {
						}
					}
				}
				else  {
					if (ngay.size() == 0) {
						l2.add(l.get(i));
					} else {
						for (int u = 0; u < ngay.size(); u++) {
							boolean f = false;
							if (d1 >= (Integer) ngay.get(u).get("batDau")
									&& d1 <= (Integer) ngay.get(u).get("KetThuc")) {
								f = true;
								if (ThoiLuong.size() == 0) {
									l2.add(l.get(i));
								} else {
									for (int k = 0; k < ThoiLuong.size(); k++) { 
										if (songay >= (Integer) ThoiLuong.get(k).get("batDau")
												&& songay <= (Integer) ThoiLuong.get(k).get("KetThuc")) {
													l2.add(l.get(i));
													break;
										} else {
											f = false;
										}
									}
								}
							}
							if (f == true) {
								break;
							}

						}
					}
				}
			}

		}
		return l2;

	}
	public boolean kiemtracon(int id) {
	    Tour t = tourRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tour"));

	    for (ThoiGianKhoiHanh data : t.getThoiGianKhoiHanh2()) {
	        if (data.getThoiGian().isAfter(LocalDateTime.now().plusHours(6)) 
	                && data.getVe().size() < t.getSoNguoiThamGia()) {
	            return true;
	        }
	    }
	    
	    return false; 
	}
}
