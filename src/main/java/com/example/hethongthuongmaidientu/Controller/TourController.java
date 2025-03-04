package com.example.hethongthuongmaidientu.Controller;

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
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.Tour;

@RestController
@RequestMapping("/tour")
public class TourController {
	@Autowired
	private TourService tourService;
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
}
