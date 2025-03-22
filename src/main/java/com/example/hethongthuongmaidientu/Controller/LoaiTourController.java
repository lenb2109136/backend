package com.example.hethongthuongmaidientu.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hethongthuongmaidientu.Service.LoaiTourService;
import com.example.hethongthuongmaidientu.model.LoaiTour;
import com.example.hethongthuongmaidientu.model.Response;

@RestController
@RequestMapping("/loaitour")
public class LoaiTourController {
    
    @Autowired
    private LoaiTourService loaiTourService;
    
    @GetMapping("/getall")    
    public ResponseEntity<Response> getAll() {
        Response response = new Response();
        try {
            response.setData(loaiTourService.getLoaiTour());
            response.setStatus(HttpStatus.OK);
            response.setMessage("OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error retrieving tour types");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addTourType(@RequestBody LoaiTour loaiTour) {
        Map<String, Object> response = new HashMap<>();
        try {
            LoaiTour savedTourType = loaiTourService.addTourType(loaiTour);
            response.put("status", "OK");
            response.put("data", savedTourType);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "ERROR");
            response.put("message", "Error adding tour type: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateTourType(@PathVariable("id") int id, 
                                                            @RequestBody LoaiTour loaiTour) {
        Map<String, Object> response = new HashMap<>();
        try {
            loaiTour.setId(id);
            LoaiTour updatedTourType = loaiTourService.updateTourType(loaiTour);
            if (updatedTourType == null) {
                response.put("status", "ERROR");
                response.put("message", "Loại tour không tồn tại");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("status", "OK");
            response.put("data", updatedTourType);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error updating tour type");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteTourType(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean deleted = loaiTourService.deleteTourType(id);
            if (!deleted) {
                response.put("status", "ERROR");
                response.put("message", "Loại tour không tồn tại");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("status", "OK");
            response.put("message", "Xóa loại tour thành công");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "ERROR");
            response.put("message", "Error deleting tour type: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}