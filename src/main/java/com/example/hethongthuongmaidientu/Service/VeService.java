package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.repository.VeRepository;

@Service
public class VeService {
	@Autowired
	private VeRepository veRepository;
}
