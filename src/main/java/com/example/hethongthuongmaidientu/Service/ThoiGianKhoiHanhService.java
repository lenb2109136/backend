package com.example.hethongthuongmaidientu.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.repository.KhachHangRepository;
import com.example.hethongthuongmaidientu.repository.ThoiGianKhoiHanhRepository;
import com.example.hethongthuongmaidientu.repository.TourRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ThoiGianKhoiHanhService {
	@Autowired
	private ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepository;

	@Autowired
	private KhachHangRepository khachHangRepository;

	@Autowired
	private TourRepository tourRepository;

	public boolean kiemtratrung(ThoiGianKhoiHanh t, String idkh, List<ThoiGianKhoiHanh> ttt) {
		List<ThoiGianKhoiHanh> tt = thoiGianKhoiHanhRepository.gettrung(t.getTour().getId(), idkh);

		tt.addAll(ttt);
		if (tt.size() == 0) {
			return true;
		} else {
			for (int i = 0; i < tt.size(); i++) {
				if (tt.get(i).getId() == t.getId() ||
						(!t.getThoiGian().toLocalDate().isBefore(tt.get(i).getThoiGian().toLocalDate()) &&
								!t.getThoiGian().toLocalDate()
										.isAfter((tt.get(i).getThoiGian().plusDays(tt.get(i).getTour().getSoNgay())
												.toLocalDate())))
						|| (!tt.get(i).getThoiGian().toLocalDate().isBefore(t.getThoiGian().toLocalDate()) &&
								!(tt.get(i).getThoiGian()).toLocalDate()
										.isAfter((t.getThoiGian().plusDays(t.getTour().getSoNgay())).toLocalDate()))
						|| (!(tt.get(i).getThoiGian().plusDays(tt.get(i).getTour().getSoNgay())).toLocalDate()
								.isBefore(t.getThoiGian().toLocalDate()) &&
								!!(tt.get(i).getThoiGian().plusDays(tt.get(i).getTour().getSoNgay())).toLocalDate()
										.isAfter((t.getThoiGian().plusDays(t.getTour().getSoNgay())).toLocalDate()))) {
					System.out.println("KHÔNG QUA ĐƯỢC");
					return false;
				}
			}
		}
		System.out.println("qua được ");
		return true;
	}

	public boolean loctour(int idtour, String sdt) {
		KhachHang k = khachHangRepository.findBySoDienThoai(sdt);
		List<ThoiGianKhoiHanh> thoiGianKhoiHanhs = thoiGianKhoiHanhRepository.getThoiGianKhoiHanh(k.getId());

		Tour t = tourRepository.findById(idtour).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tour"));
		for (int i = 0; i < t.getThoiGianKhoiHanh2().size(); i++) {
			if (kiemtratrung(t.getThoiGianKhoiHanh2().get(i), sdt, thoiGianKhoiHanhs) == true) {
				return true;
			}
		}
		return false;
	}
}
