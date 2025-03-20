package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.TourService;
import com.example.hethongthuongmaidientu.model.CHAN;
import com.example.hethongthuongmaidientu.model.GiaUuDai;
import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.repository.ChanRepository;
import com.example.hethongthuongmaidientu.repository.GiaUuDaiRepository;
import com.example.hethongthuongmaidientu.repository.KhachHangRepository;
import com.example.hethongthuongmaidientu.repository.ThoiGianKhoiHanhRepository;
import com.example.hethongthuongmaidientu.repository.TourRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tour")
public class TourController {
	@Autowired
	private TourService tourService;

	@Autowired
	GiaUuDaiRepository giaUuDaiRepo;

	@Autowired
	private KhachHangRepository khachHangRepository;

	@Autowired
	private TourRepository tourRepository;

	@Autowired
	ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepo;

	@Autowired
	ChanRepository chanRepo;

	public boolean checkDate(LocalDateTime date1, LocalDateTime date2) {
		return date1.isBefore(date2) && date1.isAfter(LocalDateTime.now());
	}

	public boolean checkDateLocal(LocalDate date1, LocalDate date2) {
		return date1.isBefore(date2) && date1.isAfter(LocalDate.now());
	}

	@GetMapping("/getl")
	public ResponseEntity<Response> getListTou(@RequestParam("id") int id) {

		Response r = new Response();
		r.setData(tourRepository.getalllis(id));
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	public boolean checkNhanVienTrungCa(Integer day, Integer index, List<ThoiGianKhoiHanh> thoiGianKhoiHanh,
			LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, Integer nhanVienId) {
		for (int i = 0; i < thoiGianKhoiHanh.size(); i++) {
			ThoiGianKhoiHanh t = thoiGianKhoiHanh.get(i);
			LocalDateTime ngayKetThucNow = t.getThoiGian().plusDays(day);
			if (i != index) {
				if ((!t.getThoiGian().isAfter(ngayBatDau) && !ngayKetThucNow.isBefore(ngayBatDau)) ||
						!t.getThoiGian().isBefore(ngayBatDau) && !t.getThoiGian().isAfter(ngayKetThuc)) {
					return false;
				}

			}
		}
		List<ThoiGianKhoiHanh> l = tourRepository.getThoiGianKhoiHanhByNhanVien(nhanVienId,
				thoiGianKhoiHanh.stream().filter(v -> v.getId() != null).toList());
		for (int i = 0; i < l.size(); i++) {
			ThoiGianKhoiHanh t = l.get(i);
			LocalDateTime ngayKetThucNow = t.getThoiGian().plusDays(day);
			if ((!t.getThoiGian().isAfter(ngayBatDau) && !ngayKetThucNow.isBefore(ngayBatDau)) ||
					!t.getThoiGian().isBefore(ngayBatDau) && !t.getThoiGian().isAfter(ngayKetThuc)) {
				return false;
			}
		}

		return true;
	}

	public boolean checkTrungUuDai(List<GiaUuDai> giaUuDais, GiaUuDai g, int index) {
		for (int i = 0; i < giaUuDais.size(); i++) {
			GiaUuDai t = giaUuDais.get(i);
			if (i != index) {
				if ((!t.getNgayGioApDung().isAfter(g.getNgayGioApDung())
						&& !t.getNgayKetThuc().isBefore(g.getNgayKetThuc())) ||
						!t.getNgayGioApDung().isBefore(g.getNgayGioApDung())
								&& !t.getNgayGioApDung().isAfter(g.getNgayKetThuc())) {
					return false;
				}
			}

		}
		return true;
	}

	@Transactional
	@PostMapping("/update")
	public ResponseEntity<Object> update(@Valid @RequestBody Tour tour) {
		System.out.println("CON CHO");
		int p = 0;
		for (ThoiGianKhoiHanh t : tour.getThoiGianKhoiHanh2()) {
			if (t.getThoiGian().isBefore(LocalDateTime.now())) {
				return new ResponseEntity<>("Thời gian khởi hành không hợp lệ", HttpStatus.BAD_REQUEST);
			}
			if (!checkNhanVienTrungCa(tour.getSoNgay(), p, tour.getThoiGianKhoiHanh2(), t.getThoiGian(),
					t.getThoiGian().plusDays(tour.getSoNgay()), t.getNhanVien().getId())) {
				return new ResponseEntity<>("Nhân viên bị trùng lịch tour", HttpStatus.BAD_REQUEST);
			}
			t.setTour(tour);
			int i = 0;
			for (GiaUuDai b : t.getGiaUuDai()) {
				if (!checkTrungUuDai(t.getGiaUuDai(), b, i)) {
					return new ResponseEntity<>("Thời gian ưu đãi không được trùng", HttpStatus.BAD_REQUEST);
				}
				b.setThoiGianKhoiHanhl(t);
				if (!checkDate(b.getNgayGioApDung(), b.getNgayKetThuc())) {
					return new ResponseEntity<>("Thời gian ưu đãi không hợp lệ", HttpStatus.BAD_REQUEST);
				}
				if (b.getGia() < 0) {
					return new ResponseEntity<>("Giá ưu đãi ko hợp lệ", HttpStatus.BAD_REQUEST);
				}
				i++;
			}
			p++;
		}
		System.out.println("HELLO WOLD");
		for (CHAN c : tour.getChan()) {
			c.setTour(tour);
			if (!checkDateLocal(c.getNgayBatDau(), c.getNgayKetThuc())) {
				return new ResponseEntity<>("Thời gian chặn không hợp lệ", HttpStatus.BAD_REQUEST);
			}
		}
		tourRepository.save(tour);
		thoiGianKhoiHanhRepo.saveAll(tour.getThoiGianKhoiHanh2());
		tour.getThoiGianKhoiHanh2().forEach(v -> {
			giaUuDaiRepo.saveAll(v.getGiaUuDai());
		});
		chanRepo.saveAll(tour.getChan());
		tourRepository.deleteThoiGianKhoiHanhNotInList(tour.getThoiGianKhoiHanh2(), tour);
		tourRepository.deleteChanNotInList(tour.getChan(), tour);
		return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
	}

	@PostMapping("add")
	@Transactional
	public ResponseEntity<Object> addTour(@Valid @RequestBody Tour tour, BindingResult bindingRel) {
		if (bindingRel.hasErrors()) {
			String errorMessage = bindingRel.getFieldErrors().get(0).getDefaultMessage();
			System.out.println("có lỗ");
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		int p = 0;
		for (ThoiGianKhoiHanh t : tour.getThoiGianKhoiHanh2()) {
			if (t.getThoiGian().isBefore(LocalDateTime.now())) {
				return new ResponseEntity<>("Thời gian khởi hành không hợp lệ", HttpStatus.BAD_REQUEST);
			}
			if (!checkNhanVienTrungCa(tour.getSoNgay(), p, tour.getThoiGianKhoiHanh2(), t.getThoiGian(),
					t.getThoiGian().plusDays(tour.getSoNgay()), t.getNhanVien().getId())) {
				return new ResponseEntity<>("Nhân viên bị trùng lịch tour", HttpStatus.BAD_REQUEST);
			}
			t.setTour(tour);
			int i = 0;
			for (GiaUuDai b : t.getGiaUuDai()) {
				if (!checkTrungUuDai(t.getGiaUuDai(), b, i)) {
					return new ResponseEntity<>("Thời gian ưu đãi không được trùng", HttpStatus.BAD_REQUEST);
				}
				b.setThoiGianKhoiHanhl(t);
				if (!checkDate(b.getNgayGioApDung(), b.getNgayKetThuc())) {
					return new ResponseEntity<>("Thời gian ưu đãi không hợp lệ", HttpStatus.BAD_REQUEST);
				}
				if (b.getGia() < 0) {
					return new ResponseEntity<>("Giá ưu đãi ko hợp lệ", HttpStatus.BAD_REQUEST);
				}
				i++;
			}
			p++;
		}
		for (CHAN c : tour.getChan()) {
			c.setTour(tour);
			if (!checkDateLocal(c.getNgayBatDau(), c.getNgayKetThuc())) {
				return new ResponseEntity<>("Thời gian chặn không hợp lệ", HttpStatus.BAD_REQUEST);
			}
		}
		tourRepository.save(tour);
		thoiGianKhoiHanhRepo.saveAll(tour.getThoiGianKhoiHanh2());
		tour.getThoiGianKhoiHanh2().forEach(v -> {
			giaUuDaiRepo.saveAll(v.getGiaUuDai());
		});
		chanRepo.saveAll(tour.getChan());
		return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
	}

	@GetMapping("/getbyid")
	public ResponseEntity<Object> getTourById(@RequestParam(name = "id", defaultValue = "-1") Integer id) {

		return new ResponseEntity<Object>(tourRepository.findById(id).orElse(null), HttpStatus.OK);
	}

	@GetMapping("/gethometour")
	public ResponseEntity<Response> getHomeTour() {

		Response r = new Response();
		r.setData(tourService.getHomeTour());
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	@GetMapping("/getListTour")
	public ResponseEntity<Response> getListTour() {

		Response r = new Response();
		r.setData(tourService.getListTour());
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	@GetMapping("/getListTourByLoai")
	public ResponseEntity<Response> getListTourByLoai(@RequestParam("idloai") int id) {
		Response r = new Response();
		r.setData(tourService.getListTour(id));
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	@GetMapping("/getListTourfavourite")
	public ResponseEntity<Response> getListTourfavourite() {

		Response r = new Response();
		r.setData(tourService.getListTour());
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	@GetMapping("/getinfortour")
	public ResponseEntity<Response> getInforTour(@RequestParam("id") int id,
			@RequestParam(name = "idnv", required = false) String idnv) {
		Tour t = tourService.getInforTour(id);
		KhachHang k = khachHangRepository.findBySoDienThoai(idnv);
		List<ThoiGianKhoiHanh> ttGianKhoiHanhs = thoiGianKhoiHanhRepository.getThoiGianKhoiHanh(k.getId());
		t.getThoiGianKhoiHanh2().removeIf((data) -> {
			if (data.getThoiGian().isAfter(LocalDateTime.now().plusHours(6))
					&& data.getVe().size() < t.getSoNguoiThamGia()
					&& thoiGianKhoiHanhService.kiemtratrung(data, idnv, ttGianKhoiHanhs)) {
				return false;
			}
			return true;
		});
		Response r = new Response();
		r.setData(tourService.getInforTour(id));
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);

	}

	@PostMapping("/getfilter")
	public ResponseEntity<Response> getInforTour(@RequestBody Map<String, Object> map) {
		Response r = new Response();
		r.setData(tourService.getByFilter(map));
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);

	}

	@GetMapping("/getAll")
	public ResponseEntity<Response> getAllAtribute() {
		Response r = new Response();
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	@GetMapping("/getadmintour")
	public ResponseEntity<Response> getAllAdmin() {
		Response r = new Response();
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		r.setData(tourRepository.getadmintour());
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	// @PostMapping("/uudai/update")
	// public ResponseEntity<Object> updateUuDaiTour(@RequestBody GiaUuDai giaUuDai)
	// {
	// System.out.println("dkcdhuichu");
	// GiaUuDai g = giaUuDaiRepo.findById(giaUuDai.getId()).orElse(null);
	// if (g != null) {
	// if (g.getNgayKetThuc().isAfter(LocalDateTime.now())) {
	// if (giaUuDai.getGia() > 0 &&
	// giaUuDai.getNgayGioApDung().isBefore(giaUuDai.getNgayKetThuc())
	// && !g.getNgayKetThuc().isBefore(giaUuDai.getNgayKetThuc())) {
	// giaUuDai.setThoiGianKhoiHanhl(g.getThoiGianKhoiHanhl());
	// giaUuDaiRepo.save(giaUuDai);
	// System.out.println("sdjcdhchidh");
	// return new ResponseEntity<Object>("Cap nhat thanh cong", HttpStatus.OK);
	// }
	// return new ResponseEntity<Object>("Thời gian ko hợp lệkk",
	// HttpStatus.BAD_REQUEST);
	// }
	// return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	// }
	// return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	// }

	@PostMapping("/uudai/update")
	public ResponseEntity<Response> updateUuDaiTour(@RequestBody GiaUuDai giaUuDai) throws Exception {
		System.out.println("dkcdhuichu");
		GiaUuDai g = giaUuDaiRepo.findById(giaUuDai.getId()).orElse(null);

		if (giaUuDai.getNgayKetThuc().isAfter(g.getThoiGianKhoiHanhl().getThoiGian())) {
			throw new Exception("Thời gian ưu đãi phải nhỏ hơn ngày khởi hành");
		}
		if (giaUuDai.getNgayKetThuc().isAfter(LocalDateTime.now())) {
			g.setNgayKetThuc(giaUuDai.getNgayKetThuc());
		}

		else {
			throw new Exception("Ngày kết thúc phải sau thời điểm hiện tại");
		}
		System.out.println(g.getThoiGianKhoiHanhl().getGiaUuDai().size());
		if (Math.round(giaUuDai.getGia()) != Math.round(g.getGia()) && g.getThoiGianKhoiHanhl().getVe().size() != 0) {
			throw new Exception("Giá không thể thay đổi do đã được sử dụng");
		} else {
			g.setGia(giaUuDai.getGia());
		}
		giaUuDaiRepo.save(g);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK, "ok", null), HttpStatus.OK);
	}

}
