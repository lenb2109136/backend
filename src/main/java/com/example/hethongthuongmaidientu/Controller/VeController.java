package com.example.hethongthuongmaidientu.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/getall")
    public ResponseEntity<Map<String, Object>> getAllBookings() {
        List<VE> bookings = veService.getAllBookings();
        Map<String, Object> response = new HashMap<>();
        response.put("data", bookings);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteBooking(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            veService.deleteBooking(id);
            response.put("status", "OK");
            response.put("message", "Xóa vé thành công");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi xóa vé: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateBooking(@PathVariable int id, @RequestBody VE updatedVe) {
        Map<String, Object> response = new HashMap<>();
        try {
            VE ve = veService.updateBooking(id, updatedVe);
            response.put("status", "OK");
            response.put("data", ve);
            response.put("message", "Cập nhật vé thành công");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi cập nhật vé: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Response> save(@RequestBody ThongTinDatVe t) {
        VE v = veService.themVeMoi(t);
        Map<Object, Object> d = new HashMap<>();
        d.put("mave", v.getId());
        Response r = new Response();
        r.setData(d);
        r.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/huyve")
    public ResponseEntity<Response> huyVe(@RequestParam("id") int idve) {
        VE v = veRepository.findById(idve)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vé của khách hàng"));
        veRepository.delete(v);
        Response r = new Response();
        r.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/ls")
    public ResponseEntity<Response> huyVe(@RequestParam("id") int id,
                                          @RequestParam("bd") LocalDateTime bd,
                                          @RequestParam("kt") LocalDateTime kt) {
        Response r = new Response();
        r.setData(veRepository.getve(id, bd, kt));
        r.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping("/getvebyid")
    public ResponseEntity<Response> getVeById(@RequestParam("id") int id) {
        VE v = veRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vé cần tìm"));
        Response r = new Response();
        VEDTO2 d = new VEDTO2();
        d.setTour(v.getThoiGianKhoiHanh().getTour());
        d.setVe(v);
        d.setThoiGianKhoiHanh(v.getThoiGianKhoiHanh());
        r.setData(d);
        r.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping("/getvenew")
    public ResponseEntity<Response> getVeBy(@RequestParam("id") int id) {
        VE v = veRepository.getvenew(id);
        Response r = new Response();
        r.setData(v);
        r.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}