package com.example.hethongthuongmaidientu.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.Service.DichVuService;
import com.example.hethongthuongmaidientu.model.DichVu;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.ServiceSort;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.repository.DichVuRepository;
import com.example.hethongthuongmaidientu.repository.TourRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/dichvu")
public class DichVuController {
	@Autowired
	private DichVuService dichVuService;
	
	@Autowired
	private DichVuRepository dichVuRepository;
	
	@Autowired
	private ServiceSort serviceSort;
	
	@Autowired
	private TourRepository tourRepository;
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
	
	@GetMapping("/gettheotour")
	public ResponseEntity<Response> getPhuHop(@RequestParam("id") int id){
		Tour t= tourRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Không tìm thấy tour cần lọc"));
		List<DichVu> dv= new ArrayList<DichVu>();
		List<DichVu> temp=dichVuService.getDichVuPhuHop();
		
		Response r= new Response();
		r.setData(dv);
		r.setStatus(HttpStatus.OK);
		return new ResponseEntity<Response>(r,HttpStatus.OK);
	}
	
	@GetMapping("/phuhop")
	public ResponseEntity<Response> getPhuHopByTour(@RequestParam("id") int idtour){
		Tour t= tourRepository.findById(idtour).orElseThrow(()-> new EntityNotFoundException("Không tìm thấy tour"));
		List<DichVu> dv= dichVuRepository.findAll();
		serviceSort.sort(dv, t.getTags());
		return new ResponseEntity<Response>( new Response(HttpStatus.OK, "OK", dv),HttpStatus.OK);
	}

}
