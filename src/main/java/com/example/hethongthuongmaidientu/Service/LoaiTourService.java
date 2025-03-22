package com.example.hethongthuongmaidientu.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.LoaiTour;
import com.example.hethongthuongmaidientu.repository.LoaiTourRepository;

@Service
public class LoaiTourService {
    @Autowired
    private LoaiTourRepository loaiTourRepository;
    
    public List<LoaiTour> getLoaiTour() {
        return loaiTourRepository.findAll();
    }
    
    public LoaiTour addTourType(LoaiTour loaiTour) {
        // Lấy id lớn nhất hiện tại
        Integer maxId = loaiTourRepository.findMaxId();
        int newId = (maxId == null) ? 1 : maxId + 1;
        
        // Gán id mới cho loaiTour
        loaiTour.setId(newId);
        
        // Lưu vào database
        return loaiTourRepository.save(loaiTour);
    }
    
    public LoaiTour updateTourType(LoaiTour loaiTour) {
        if (!loaiTourRepository.existsById(loaiTour.getId())) {
            return null;
        }
        return loaiTourRepository.save(loaiTour);
    }
    
    public boolean deleteTourType(int id) {
        if (!loaiTourRepository.existsById(id)) {
            return false;
        }
        loaiTourRepository.deleteById(id);
        return true;
    }
}