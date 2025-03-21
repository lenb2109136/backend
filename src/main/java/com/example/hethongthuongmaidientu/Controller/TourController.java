package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.example.hethongthuongmaidientu.Service.ThoiGianKhoiHanhService;
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
	
	@Autowired
	private ThoiGianKhoiHanhService thoiGianKhoiHanhService;
	
	@Autowired
	private ThoiGianKhoiHanhRepository thoiGianKhoiHanhRepository;

	public boolean checkDate(LocalDateTime date1, LocalDateTime date2) {
		return date1.isBefore(date2) && date1.isAfter(LocalDateTime.now());
	}

	public boolean checkDateLocal(LocalDate date1, LocalDate date2) {
		return date1.isBefore(date2) && date1.isAfter(LocalDate.now());
	}
		//chưa check
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
			if (i != index && (!t.getNgayGioApDung().isAfter(g.getNgayGioApDung())
					&& !t.getNgayKetThuc().isBefore(g.getNgayKetThuc())) ||
					!t.getNgayGioApDung().isBefore(g.getNgayGioApDung())
							&& !t.getNgayGioApDung().isAfter(g.getNgayKetThuc())) {
				return false;
			}
		}
		return true;
	}
	public boolean checkTrungUuDai2(List<GiaUuDai> giaUuDais, LocalDateTime t) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < giaUuDais.size(); i++) {
            GiaUuDai g1 = giaUuDais.get(i);
            for (int j = i + 1; j < giaUuDais.size(); j++) {
                GiaUuDai g2 = giaUuDais.get(j);

                if (!(g1.getNgayKetThuc().isBefore(g2.getNgayGioApDung()) || 
                      g2.getNgayKetThuc().isBefore(g1.getNgayGioApDung()))) {
                    throw new IllegalArgumentException(
                        "Thời gian ưu đãi bị trùng tại ngày " + g1.getNgayGioApDung().format(formatter)
                    );
                }
            }

            if (!t.isBefore(g1.getNgayGioApDung())&& !t.isAfter(g1.getNgayKetThuc())) {
                throw new IllegalArgumentException("Thời gian ưu đãi thứ : "+(i+1)+" không phù hợp với ngày khởi hành "+t.format(formatter));
            }
        }

        return true; 
    }
	public void validateChans(List<CHAN> chans, int totalDays) throws Exception {
	    if (chans == null || chans.isEmpty()) {
	        throw new IllegalArgumentException("Danh sách chặn không được rỗng.");
	    }

	    chans.sort(Comparator.comparingInt(CHAN::getNgayBatDau));
	    Set<Integer> coveredDays = new HashSet<>();
	    int prevEnd = 0; 

	    for (int i = 0; i < chans.size(); i++) {
	        CHAN chan = chans.get(i);
	        if (chan.getMoTa() == null || chan.getMoTa().length() == 0) {
	            throw new Exception("Mô tả chặn thứ " + (i + 1) + " không được để trống.");
	        }

	        int start = chan.getNgayBatDau();
	        int end = chan.getNgayKetThuc();

	        if (start > end) {
	            throw new IllegalArgumentException("Chặn thứ " + (i + 1) + " có ngày bắt đầu lớn hơn ngày kết thúc.");
	        }

	        if (end > totalDays) {
	            throw new IllegalArgumentException("Chặn thứ " + (i + 1) + " bị vượt số ngày của tour.");
	        }

	        if (start > prevEnd + 1) {
	            throw new IllegalArgumentException("Chặn thứ " + (i + 1) + " bị thiếu ngày từ " + (prevEnd + 1) + " đến " + (start - 1));
	        }

	        for (int j = start; j <= end; j++) {
	            if (!coveredDays.add(j)) {
	                throw new IllegalArgumentException("Chặn thứ " + (i + 1) + " có ngày trùng với chặn khác.");
	            }
	        }

	        prevEnd = end;
	    }

	    for (int day = 1; day <= totalDays; day++) {
	        if (!coveredDays.contains(day)) {
	            throw new IllegalArgumentException("Chặn bị thiếu ngày thứ " + day);
	        }
	    }
	}

	@Transactional
	@PostMapping("/update")
	
	public ResponseEntity<Object> update(@Valid @RequestBody Tour tour,BindingResult bindingRel) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		if (bindingRel.hasErrors()) {
			String errorMessage = bindingRel.getFieldErrors().get(0).getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		int p = 0;
		if(tour.getMoTa()==null||tour.getMoTa().length()==0) {
			throw new Exception("Vui lòng cung cấp thông tin mô tả cho tour");
		}
		for (ThoiGianKhoiHanh t : tour.getThoiGianKhoiHanh2()) {
			if (t.getThoiGian().isBefore(LocalDateTime.now())) {
				return new ResponseEntity<>("Thời gian khởi hành không hợp lệ", HttpStatus.BAD_REQUEST);
			}
			if (!checkNhanVienTrungCa(tour.getSoNgay(), p, tour.getThoiGianKhoiHanh2().stream().filter(v->v.getId()==null).toList(), t.getThoiGian(),
					t.getThoiGian().plusDays(tour.getSoNgay()), t.getNhanVien().getId())) {
				return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST,"Nhân viên "+t.getNhanVien().getTen()+" đã có lịch hướng dẫn trùng", null), HttpStatus.OK);
			}
			t.setTour(tour);
			int i = 0;
			checkTrungUuDai2(t.getGiaUuDai(),t.getThoiGian());
			for(int f=0;f<t.getGiaUuDai().size();f++) {
				if(t.getGiaUuDai().get(f).getGia()>=t.getGia()) {
					throw new Exception("Giá ưu đãi thứ: "+(f+1)+ " của thời gian khởi hành: "+t.getThoiGian().format(formatter));
				}
			}
			p++;
		}
		for(int y=0;y<tour.getChan().size();y++) {
			tour.getChan().get(y).setTour(tour);
		}
		
		validateChans(tour.getChan(), tour.getSoNgay());
		tourRepository.save(tour);
		thoiGianKhoiHanhRepo.saveAll(tour.getThoiGianKhoiHanh2());
		tour.getThoiGianKhoiHanh2().forEach(v -> {
			giaUuDaiRepo.saveAll(v.getGiaUuDai());
		});
		chanRepo.saveAll(tour.getChan());
		Response r= new Response(HttpStatus.OK,"ok",null);
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@PostMapping("add")
	@Transactional
	public ResponseEntity<Object> addTour(@Valid @RequestBody Tour tour, BindingResult bindingRel) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		if (bindingRel.hasErrors()) {
			String errorMessage = bindingRel.getFieldErrors().get(0).getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		int p = 0;
		if(tour.getMoTa()==null||tour.getMoTa().length()==0) {
			throw new Exception("Vui lòng cung cấp thông tin mô tả cho tour");
		}
		for (ThoiGianKhoiHanh t : tour.getThoiGianKhoiHanh2()) {
			if (t.getThoiGian().isBefore(LocalDateTime.now())) {
				return new ResponseEntity<>("Thời gian khởi hành không hợp lệ", HttpStatus.BAD_REQUEST);
			}
			if (!checkNhanVienTrungCa(tour.getSoNgay(), p, tour.getThoiGianKhoiHanh2(), t.getThoiGian(),
					t.getThoiGian().plusDays(tour.getSoNgay()), t.getNhanVien().getId())) {
				return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST,"Nhân viên "+t.getNhanVien().getTen()+" đã có lịch hướng dẫn trùng", null), HttpStatus.OK);
			}
			t.setTour(tour);
			int i = 0;
			checkTrungUuDai2(t.getGiaUuDai(),t.getThoiGian());
			for(int f=0;f<t.getGiaUuDai().size();f++) {
				if(t.getGiaUuDai().get(f).getGia()>=t.getGia()) {
					throw new Exception("Giá ưu đãi thứ: "+(f+1)+ " của thời gian khởi hành: "+t.getThoiGian().format(formatter));
				}
			}
			p++;
		}
		for(int y=0;y<tour.getChan().size();y++) {
			tour.getChan().get(y).setTour(tour);
		}
		
		validateChans(tour.getChan(), tour.getSoNgay());
		tourRepository.save(tour);
		thoiGianKhoiHanhRepo.saveAll(tour.getThoiGianKhoiHanh2());
		tour.getThoiGianKhoiHanh2().forEach(v -> {
			giaUuDaiRepo.saveAll(v.getGiaUuDai());
		});
		chanRepo.saveAll(tour.getChan());
		Response r= new Response(HttpStatus.OK,"ok",null);
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@GetMapping("/getbyid")
	public ResponseEntity<Object> getTourById(@RequestParam(name = "id", defaultValue = "-1") Integer id) {

		return new ResponseEntity<Object>(tourRepository.findById(id).orElse(null), HttpStatus.OK);
	}

	
	//ĐÃ CHECK ĐIỀU KIỆN-2
	@GetMapping("/gethometour")
	public ResponseEntity<Response> getHomeTour(@RequestParam("sdt") String sdt) {
		List<Map<Object, Object>> mapList = tourService.getHomeTour();
		mapList.removeIf(map -> {
			if(thoiGianKhoiHanhService.loctour((Integer)map.get("T_ID"), sdt)==false
					|| tourService.kiemtracon((Integer) map.get("T_ID"))==false) {
				return true;
			}
			return false;
		});
		Response r = new Response();
		r.setData(mapList);
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	
	//ĐÃ CHECK ĐIỀU KIỆN ĐẦY ĐỦ -2
	@GetMapping("/getListTour")
	public ResponseEntity<Response> getListTour(@RequestParam("sdt") String sdt) {
		List<Map<Object, Object>> mapList = tourService.getListTour();
		mapList.removeIf(map -> {
			if(thoiGianKhoiHanhService.loctour((Integer)map.get("T_ID"), sdt)==false
					|| tourService.kiemtracon((Integer) map.get("T_ID"))==false) {
				return true;
			}
			return false;
		});
		Response r = new Response();
		r.setData(mapList);
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	@GetMapping("/getlistour2")
	public ResponseEntity<Response> getListTour(@RequestParam("cart") List<Map<Object, Object>> ma, @RequestParam("sdt") String sdt) {
		List<Map<Object, Object>> mapList = tourService.getListTour();
		mapList.removeIf(map -> {
			if(thoiGianKhoiHanhService.loctour((Integer)map.get("T_ID"), sdt)==false
					|| tourService.kiemtracon((Integer) map.get("T_ID"))==false) {
				return true;
			}
			return false;
		});
		Response r = new Response();
		r.setData(mapList);
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	
	//ĐÃ CHECK ĐIỀU KIỆN-2
	@GetMapping("/getListTourByLoai")
	public ResponseEntity<Response> getListTourByLoai(@RequestParam("idloai") int id,@RequestParam("sdt") String sdt) {
		List<Map<Object, Object>> mapList = tourService.getListTour(id);
		mapList.removeIf(map -> {
			if(thoiGianKhoiHanhService.loctour((Integer)map.get("T_ID"), sdt)==false
					|| tourService.kiemtracon((Integer) map.get("T_ID"))==false) {
				return true;
			}
			return false;
		});
		Response r = new Response();
		r.setData(mapList);
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	
	//CHƯA CHECK----------------------------------------------------------------------------
	@GetMapping("/getListTourfavourite")
	public ResponseEntity<Response> getListTourfavourite() {

		Response r = new Response();
		r.setData(tourService.getListTour());
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	//ĐÃ CHECK-2
	@GetMapping("/getinfortour")
	public ResponseEntity<Response> getInforTour(@RequestParam("id") int id, @RequestParam(name = "idnv",required = false) String idnv) {
			Tour t=tourService.getInforTour(id);
			KhachHang k =khachHangRepository.findBySoDienThoai(idnv);
			System.out.println("id nhân viên; "+k);
			List<ThoiGianKhoiHanh> ttGianKhoiHanhs=thoiGianKhoiHanhRepository.getThoiGianKhoiHanh(k.getId());
			t.getThoiGianKhoiHanh2().removeIf((data)->{
				if(data.getThoiGian().isAfter(LocalDateTime.now().plusHours(6))&&data.getVe().size()<t.getSoNguoiThamGia()
					&&thoiGianKhoiHanhService.kiemtratrung(data,idnv,ttGianKhoiHanhs)) {
					return false;
				}
				return true;
			});
			
		Response r = new Response();
		r.setData(t);
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);

	}
	//ĐÃ CHECK-2
	@PostMapping("/getfilter")
	public ResponseEntity<Response> getInforTour(@RequestBody Map<String, Object> map) {
		System.out.println((String) map.get("sdt"));
		List<Map<Object, Object>> mapList = tourService.getByFilter(map);
		mapList.removeIf(mapT -> {
			if(thoiGianKhoiHanhService.loctour((Integer)mapT.get("T_ID"), (String) map.get("sdt"))==false
					|| tourService.kiemtracon((Integer) mapT.get("T_ID"))==false) {
				return true;
			}
			return false;
		});
		Response r = new Response();
		r.setData(mapList);
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);

	}
	
	@GetMapping("/getAllTours")
	public ResponseEntity<Response> getAllTours() {
	    List<Map<Object, Object>> mapList = tourService.getListTour();
	    mapList.removeIf(map -> tourService.kiemtracon((Integer) map.get("T_ID")) == false);
	    Response r = new Response();
	    r.setData(mapList);
	    r.setMessage("OK");
	    r.setStatus(HttpStatus.OK);
	    
	    return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

//	@GetMapping("/getAll")
//	public ResponseEntity<Response> getAllAtribute() {
//		Response r = new Response();
//		r.setMessage("OK");
//		r.setStatus(HttpStatus.OK);
//		return new ResponseEntity<Response>(r, HttpStatus.OK);
//	}

	//ĐÃ CHECK CÒN HÀNG
	@GetMapping("/getadmintour")
	public ResponseEntity<Response> getAllAdmin() {
		List<Tour> t= tourRepository.getadmintour();
		t.removeIf(data-> {
			return tourService.kiemtracon(data.getId());
		});
		Response r = new Response();
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		r.setData(t);
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
	
	@PostMapping("/getten")

	public ResponseEntity<Response> getten(@RequestParam("ten") String ten, @RequestParam("id") int idloai){
		return new ResponseEntity<Response>
		(new Response(HttpStatus.OK,"ok",tourRepository.getListTourr(ten,idloai)), HttpStatus.OK);
	}
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
	
	@GetMapping("/gettags")
	public ResponseEntity<Response> gettags(){
		return  new ResponseEntity<Response>(new Response(HttpStatus.OK,"ok",tourRepository.getTags()),HttpStatus.OK);
	}

}
