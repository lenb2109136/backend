package com.example.hethongthuongmaidientu.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		List<Map<Object, Object>> l = tourRepository.getListTourFilter((Integer) map.get("loai"));
		System.out.println("số lượng phần tử danh sách: " + l);
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
				} else {
					System.out.println("số lượng phần tử của danh sách ngày: " + ngay.size());
					if (ngay.size() == 0) {
						l2.add(l.get(i));
					} else {
						for (int u = 0; u < ngay.size(); u++) {
							boolean f = false;
							if (d1 >= (Integer) ngay.get(u).get("batDau")
									&& d1 <= (Integer) ngay.get(u).get("KetThuc")) {
								System.out.println("đã đi vào đaay so sánh ......");
								f = true;
								if (ThoiLuong.size() == 0) {
									l2.add(l.get(i));
								} else {
									for (int k = 0; k < ThoiLuong.size(); k++) {
										System.out.println("đã vào thời lượng so sánh : ");
										System.out.println("số ngày"+ songay);
										System.out.println("thòi bd: "+(Integer) ThoiLuong.get(k).get("batDau"));
										System.out.println("thòi kết thúc"+(Integer) ThoiLuong.get(k).get("KetThuc") );
										if (songay >= (Integer) ThoiLuong.get(k).get("batDau")
												&& songay <= (Integer) ThoiLuong.get(k).get("KetThuc")) {
													System.out.println("so sánh đúng");
													l2.add(l.get(i));
													break;
										} else {
											System.out.println("so sánh sai");
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
}
