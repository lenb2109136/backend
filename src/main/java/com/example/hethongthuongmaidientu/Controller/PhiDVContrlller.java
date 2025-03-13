package com.example.hethongthuongmaidientu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hethongthuongmaidientu.model.DichVu;
import com.example.hethongthuongmaidientu.model.PhiDichVu;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.VE;
import com.example.hethongthuongmaidientu.repository.DichVuRepository;
import com.example.hethongthuongmaidientu.repository.PhiDichVuRepository;
import com.example.hethongthuongmaidientu.repository.VeRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/phidichvu")
public class PhiDVContrlller {
    @Autowired
    private PhiDichVuRepository phiDichVuRepository;

    @Autowired
    private DichVuRepository dichVuRepository;

    @Autowired
    VeRepository veRepository;

    @GetMapping("/huy")
    public ResponseEntity<Response> huydichvu(@RequestParam("id") int id) {
        PhiDichVu p = phiDichVuRepository.findById(id).orElse(null);
        if (p == null) {
            throw new EntityNotFoundException("khogny ịndbfhj");
        }
        phiDichVuRepository.delete(p);
        return new ResponseEntity<Response>(new Response(HttpStatus.OK, null, null), HttpStatus.OK);
    }

    @GetMapping("/add")
    public ResponseEntity<Response> save(@RequestParam("idve") int idve, @RequestParam("iddv") int iddv) {
        DichVu d = dichVuRepository.findById(iddv)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy dịch vụ cần tìm"));
        VE v = veRepository.findById(idve).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vé cần tìm"));
        PhiDichVu p = new PhiDichVu();
        p.setDichVu(d);
        p.setGia(d.getGia());
        p.setVe(v);
        phiDichVuRepository.save(p);
        return new ResponseEntity<Response>(new Response(HttpStatus.OK, null, p), HttpStatus.OK);
    }
}