package com.example.hethongthuongmaidientu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.VeService;
import com.example.hethongthuongmaidientu.model.Response;

import DTO.ThongTinDatVe;

@RestController
@RequestMapping("/ve")
public class VeController {
		
		@Autowired
		private VeService veService;
		@PostMapping("/save")
		public ResponseEntity<Response> save(@RequestBody ThongTinDatVe t) {
			veService.themVeMoi(t);
			Response r= new Response();
			r.setStatus(HttpStatus.OK);
			return new ResponseEntity<Response>(r,HttpStatus.OK);
		}
}
