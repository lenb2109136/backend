package com.example.hethongthuongmaidientu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.model.NHANVIEN;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.repository.NhanVienRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class NhanVienController {

    @Autowired
    NhanVienRepository nhanVienRepo;

    @GetMapping("nhanvien/getall")
    public ResponseEntity<Object> getAllNhanVien() {
        return new ResponseEntity<>(nhanVienRepo.findAll(), HttpStatus.OK);
    }
    
  
}
