package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.TourService;
import com.example.hethongthuongmaidientu.model.GiaUuDai;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.repository.GiaUuDaiRepository;
import com.example.hethongthuongmaidientu.repository.TourRepository;

@RestController
@RequestMapping("/tour")
public class TourController {
	@Autowired
	private TourService tourService;
	
	@Autowired
	GiaUuDaiRepository giaUuDaiRepo;
	
	@Autowired
	private TourRepository tourRepository;
	@GetMapping("/gethometour")
	public ResponseEntity<Response> getHomeTour(){
		
		Response r= new Response();
		r.setData(tourService.getHomeTour());
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	@GetMapping("/getListTour")
	public ResponseEntity<Response> getListTour(){
		
		Response r= new Response();
		r.setData(tourService.getListTour());
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
	@GetMapping("/getListTourByLoai")
	public ResponseEntity<Response> getListTourByLoai(@RequestParam("idloai") int id){
		Response r= new Response();
		r.setData(tourService.getListTour(id));
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	@GetMapping("/getListTourfavourite")
	public ResponseEntity<Response> getListTourfavourite(){
		
		Response r= new Response();
		r.setData(tourService.getListTour());
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
	@GetMapping("/getinfortour")
	public ResponseEntity<Response> getInforTour(@RequestParam("id") int id) {
		
		Response r= new Response();
		r.setData(tourService.getInforTour(id));
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
		
	}
	
	@PostMapping("/getfilter")
	public ResponseEntity<Response> getInforTour(@RequestBody Map<String,Object> map) {
		Response r= new Response();
		r.setData(tourService.getByFilter(map));
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Response> getAllAtribute(){
		Response r= new Response();
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
	@GetMapping("/getadmintour")
	public ResponseEntity<Response> getAllAdmin(){
		Response r= new Response();
		r.setMessage("OK");
		r.setStatus(HttpStatus.OK);
		r.setData(tourRepository.getadmintour());
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
	@PostMapping("/uudai/update")
	public ResponseEntity<Object> updateUuDaiTour(@RequestBody GiaUuDai giaUuDai){
		System.out.println("dkcdhuichu");
		 GiaUuDai g=giaUuDaiRepo.findById(giaUuDai.getId()).orElse(null);
		 if(g!=null) {
			 if(g.getNgayKetThuc().isAfter(LocalDateTime.now())){
				 if(giaUuDai.getGia()>0&&giaUuDai.getNgayGioApDung().isBefore(giaUuDai.getNgayKetThuc())&&!g.getNgayKetThuc().isBefore(giaUuDai.getNgayKetThuc())) {
					 giaUuDai.setThoiGianKhoiHanhl(g.getThoiGianKhoiHanhl());
					 giaUuDaiRepo.save(giaUuDai);
					 System.out.println("sdjcdhchidh");
					 return new ResponseEntity<Object>("Cap nhat thanh cong",HttpStatus.OK);
				 }
				 return new ResponseEntity<Object>("Thời gian ko hợp lệkk",HttpStatus.BAD_REQUEST);
			 }
			 return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		 }
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	
	
}
