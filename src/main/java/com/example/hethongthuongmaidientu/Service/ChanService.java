package com.example.hethongthuongmaidientu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hethongthuongmaidientu.repository.ChanRepository;

@Service
public class ChanService {
	@Autowired
	private ChanRepository chanRepository;
}
