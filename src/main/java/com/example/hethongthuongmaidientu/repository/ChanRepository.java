package com.example.hethongthuongmaidientu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.CHAN;

@Repository
public interface ChanRepository extends JpaRepository<CHAN, Integer>{

}
