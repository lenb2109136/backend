package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.repository.LichSuTimKiem;

@Service
public class LichSuTimKiemService {
	@Autowired
	private LichSuTimKiem lichSuTimKiemrepository;
}
