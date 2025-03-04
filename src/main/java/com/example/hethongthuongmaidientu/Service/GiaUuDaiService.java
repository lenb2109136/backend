package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.model.GiaUuDai;
import com.example.hethongthuongmaidientu.repository.GiaUuDaiRepository;

@Service
public class GiaUuDaiService {
	@Autowired
	private GiaUuDaiRepository giaUuDaiRepository;
}
