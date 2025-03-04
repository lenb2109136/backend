package com.example.hethongthuongmaidientu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.PhanHoi;

@Repository
public interface PhanHoiRepository extends JpaRepository<PhanHoi, Integer>{
	@Query(value = "SELECT * FROM phanhoi WHERE PHA_PH_ID IS null  AND T_ID=:id", nativeQuery = true)
	public List<PhanHoi> getList(int id);
	
	@Query(value = "SELECT * FROM phanhoi WHERE PHA_PH_ID=:id", nativeQuery = true)
	public List<PhanHoi> getListByTour(int id);
	
}
