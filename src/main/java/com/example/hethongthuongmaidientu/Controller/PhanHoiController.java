package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.PhanHoiService;
import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.PhanHoi;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.repository.KhachHangRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/phanhoi")
public class PhanHoiController {
	@Autowired
	private PhanHoiService phanHoiService;
	
	@Autowired
	private KhachHangRepository khachHangRepository;
	@GetMapping("/getbytour")
	public ResponseEntity<Response> getByTour(@RequestParam("id") int id){
		Response r= new Response();
		r.setData(phanHoiService.getPhanHoiByTour(id));
		r.setStatus(HttpStatus.OK);
		r.setMessage("OK");
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	@GetMapping("/getbyph")
	public ResponseEntity<Response> getBy(@RequestParam("id") int id){
		Response r= new Response();
		r.setData(phanHoiService.getPhanHoiByPhanHoi(id));
		r.setStatus(HttpStatus.OK);
		r.setMessage("OK");
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
	@PostMapping("/addthem")
	public ResponseEntity<Response> addComments(@RequestParam("idcha") int idscha, @RequestParam("noidung") String noidungphanhoi){
		 if(noidungphanhoi==null ||noidungphanhoi=="") {
			 Response r= new Response();
				r.setStatus(HttpStatus.BAD_REQUEST);
				r.setMessage("Nội dung phản hồi không được để trống");
				return new ResponseEntity<Response>(r,HttpStatus.OK);
		 }
		PhanHoi cha= phanHoiService.getById(idscha);
		PhanHoi con = new PhanHoi();
		KhachHang kh= khachHangRepository.findById(1).orElseThrow(()-> new EntityNotFoundException("Không tìm thấy thông tin khách hàng"));
		con.setKhachHang(kh);
		con.setNoiDungPhanHoi(noidungphanhoi);
		con.setReply(cha);
		con.setThoiGianPhanHoi(LocalDateTime.now());
		con.setTour(cha.getTour());
		phanHoiService.savePhanHoi(con);
		Response r= new Response();
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
}

