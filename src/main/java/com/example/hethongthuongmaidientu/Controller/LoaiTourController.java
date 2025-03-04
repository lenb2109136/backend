package com.example.hethongthuongmaidientu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.LoaiTourService;
import com.example.hethongthuongmaidientu.model.Response;

@RestController
@RequestMapping("/loaitour")
public class LoaiTourController {
	@Autowired
	private LoaiTourService loaiTourService;
	@GetMapping("/getall")    
	public ResponseEntity<Response> getAll() {
		Response r= new Response();
		r.setData(loaiTourService.getLoaiTour());
		r.setStatus(HttpStatus.OK);
		r.setMessage("ok");
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
}
