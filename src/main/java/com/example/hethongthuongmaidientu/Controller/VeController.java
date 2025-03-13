package com.example.hethongthuongmaidientu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.VeService;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.VE;
import com.example.hethongthuongmaidientu.repository.VeRepository;

import DTO.ThongTinDatVe;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/ve")
public class VeController {

	@Autowired
	private VeService veService;

	@Autowired
	private VeRepository veRepository;

	@PostMapping("/save")
	public ResponseEntity<Response> save(@RequestBody ThongTinDatVe t) {
		veService.themVeMoi(t);
		Response r = new Response();
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

	@PostMapping("/huyve")
	public ResponseEntity<Response> huyVe(@RequestParam("id") int idve) {
		VE v = veRepository.findById(idve)
				.orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vé của khách hàng"));
		veRepository.delete(v);
		Response r = new Response();
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
}
