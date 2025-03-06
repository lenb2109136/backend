package com.example.hethongthuongmaidientu.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.DichVuService;
import com.example.hethongthuongmaidientu.model.DichVu;
import com.example.hethongthuongmaidientu.model.Response;

@RestController
@RequestMapping("/dichvu")
public class DichVuController {
	@Autowired
	private DichVuService dichVuService;
	@GetMapping("/getphuhop")
	public ResponseEntity<Response> getPhuHop(){
		Response r= new Response();
		r.setData(dichVuService.getDichVuPhuHop());
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	@GetMapping("/getall")
	public ResponseEntity<Response> getAll(){
		Response r= new Response();
		r.setData(dichVuService.getAll());
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
}
