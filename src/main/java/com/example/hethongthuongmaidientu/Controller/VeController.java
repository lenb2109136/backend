package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
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

import com.example.hethongthuongmaidientu.Service.VeService;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.VE;
import com.example.hethongthuongmaidientu.repository.VeRepository;

import DTO.ThongTinDatVe;
import DTO.VEDTO2;
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
		VE v= veService.themVeMoi(t);
		Map<Object,Object> d= new HashMap<Object, Object>();
		d.put("mave", v.getId());
		
		Response r = new Response();
		r.setData(d);
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

	@PostMapping("/ls")
	public ResponseEntity<Response> huyVe(@RequestParam("id") int id,
			@RequestParam("bd") LocalDateTime bd,
			@RequestParam("kt") LocalDateTime kt) {
		Response r = new Response();
		r.setData(veRepository.getve(id, bd, kt));
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	
	
	@GetMapping("getvebyid")
	public ResponseEntity<Response> getVeById(@RequestParam("id") int id){
		VE v= veRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Không tìm thấy vé cần tìm"));
		Response r = new Response();
		VEDTO2 d= new VEDTO2();
		d.setTour(v.getThoiGianKhoiHanh().getTour());
		d.setVe(v);
		d.setThoiGianKhoiHanh(v.getThoiGianKhoiHanh());
		r.setData(d);
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	@GetMapping("getvenew")
	public ResponseEntity<Response> getVeBy(@RequestParam("id") int id){
		VE v= veRepository.getvenew(id);
		Response r = new Response();
		r.setData(v);
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	

	
}
