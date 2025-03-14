package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hethongthuongmaidientu.model.DichVu;
import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.NHANVIEN;
import com.example.hethongthuongmaidientu.model.PhiDichVu;
import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.VE;
import com.example.hethongthuongmaidientu.repository.DichVuRepository;
import com.example.hethongthuongmaidientu.repository.KhachHangRepository;
import com.example.hethongthuongmaidientu.repository.NhanVienRepository;
import com.example.hethongthuongmaidientu.repository.PhiDichVuRepository;
import com.example.hethongthuongmaidientu.repository.ThoiGianKhoiHanhRepository;
import com.example.hethongthuongmaidientu.repository.TourRepository;
import com.example.hethongthuongmaidientu.repository.VeRepository;

import DTO.ThongTinDatVe;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VeService {
	@Autowired
	private VeRepository veRepository;
	
	@Autowired
	private DichVuRepository dichVuRepository;
	
	@Autowired
	private KhachHangRepository khachHangRepository;
	
	@Autowired
	private TourRepository tourRepository;
	
	@Autowired
	private PhiDichVuRepository phiDichVuRepository;
	
	@Autowired
	private ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepository;

	@Transactional
	public void themVeMoi(ThongTinDatVe t) {
		KhachHang kh = khachHangRepository.findById(t.getIdkh())
				.orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng cần đặt"));
			t.getInfove().forEach(data -> {
			ThoiGianKhoiHanh tt = thoiGianKhoiHanhRepository.findById(data.getIdtgkh())
				.orElseThrow(() -> new EntityNotFoundException("Vui lòng chọn thời gian khởi hành cho tour"));

			VE v = new VE();
			v.setKhachHang(kh);
			v.setThoiGianKhoiHanh(tt);
			System.out.println("GIÁ: "+tt.getGia());
			v.setGia(tt.getGia());
			veRepository.save(v);
			data.getDsdv().forEach(d -> {
				DichVu d1 = dichVuRepository.findById(d)
						.orElseThrow(() -> new EntityNotFoundException("Không tìm thấy dịch vụ cần đăng ký"));
				PhiDichVu pp = new PhiDichVu();
				pp.setDichVu(d1);
				System.out.println(d1.getGia());
				pp.setGia(d1.getGia());
				pp.setVe(v);
				phiDichVuRepository.save(pp);
			});
		});

	}
}
