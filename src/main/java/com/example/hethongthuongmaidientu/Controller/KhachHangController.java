package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.model.KhachHang;
import com.example.hethongthuongmaidientu.model.NHANVIEN;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.repository.KhachHangRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
public class KhachHangController {

    @Autowired
    KhachHangRepository khachHangRepo;

    @GetMapping("admin/khachhang/getall")
    public ResponseEntity<Object> getAllCustomer(@RequestParam(name = "sdt", defaultValue = "") String sdt) {
        List<KhachHang> khs = khachHangRepo.findBySoDienThoais(sdt);
        return new ResponseEntity<>(khs, HttpStatus.OK);
    }

    @PostMapping("admin/khachhang/update")
    public ResponseEntity<Object> updateCustomer(@RequestBody @Valid KhachHang khachHang, BindingResult b) {
        if (b.hasErrors()) {
            String errorMessage = b.getFieldErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Long a = ChronoUnit.YEARS.between(khachHang.getNamSinh(), LocalDate.now());
        if (a > 18) {
            khachHangRepo.save(khachHang);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Tuổi phải lớn hơn 18", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getinfor")
  	public ResponseEntity<Response> gettags(){
      	KhachHang nv= khachHangRepo.findById(KhachHang.idkh).orElseThrow(()-> new EntityNotFoundException("Không tìm thấy nhân viên"));
  ;		return  new ResponseEntity<Response>(new Response(HttpStatus.OK,"ok",nv),HttpStatus.OK);
  	}
    @PostMapping("/them")
    public ResponseEntity<Response> them(@RequestBody Map<Object, Object> map) {
    	System.out.println("Thông tin khách hàng: "+map.get("ten"));
    	return new ResponseEntity<Response>(new Response(),HttpStatus.OK);
    }
}
