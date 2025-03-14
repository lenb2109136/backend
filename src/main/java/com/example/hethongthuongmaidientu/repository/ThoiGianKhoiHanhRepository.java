package com.example.hethongthuongmaidientu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;

@Repository
public interface ThoiGianKhoiHanhRepository extends JpaRepository<ThoiGianKhoiHanh, Integer>{
	@Query(value = "SELECT tt.* FROM ve v JOIN khachhang k ON v.KH_ID=k.KH_ID JOIN thoigiankhoihanh tt ON tt.TGKH_ID=v.TGKH_ID JOIN tour t ON t.T_ID=tt.T_ID WHERE t.T_ID=:id"
	        + " AND k.KH_SDT LIKE %:idkh%", nativeQuery = true)
	public List<ThoiGianKhoiHanh> gettrung(int id, String idkh);

}
