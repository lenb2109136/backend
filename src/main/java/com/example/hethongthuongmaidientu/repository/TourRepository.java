package com.example.hethongthuongmaidientu.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer>{
	@Query(value = "SELECT t.T_ID,t.T_ANH,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_SONGUOITHAMGIA,t.T_SOSAO, COUNT(p.PH_ID) AS soluongdanhg,AVG(tt.TGKH_GIA) AS gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID LEFT JOIN phanhoi p ON p.T_ID=t.T_ID WHERE tt.TGKH_THOIGIAN>NOW() GROUP BY t.T_ID LIMIT 6",nativeQuery = true)
	public List<Map<Object, Object>> getHomeTour();
	
	@Query(value = "SELECT t.T_ID,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_ANH,AVG(tt.TGKH_GIA) as gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID  WHERE tt.TGKH_THOIGIAN>NOW() GROUP BY t.T_ID ",nativeQuery = true)
	public List<Map<Object, Object>> getListTour();
	
	@Query(value = "SELECT t.T_ID,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_ANH,AVG(tt.TGKH_GIA) as gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID  WHERE tt.TGKH_THOIGIAN>NOW() AND LT_ID=:lt GROUP BY t.T_ID ",nativeQuery = true)
	public List<Map<Object, Object>> getListTour(int lt);
	
	@Query(value = "SELECT \r\n"
			+ "    t.T_ID,\r\n"
			+ "    t.T_TEN,\r\n"
			+ "    t.T_SONGAY,\r\n"
			+ "    t.T_SODEM,\r\n"
			+ "    t.T_ANH,\r\n"
			+ "    DATE_FORMAT(tt.TGKH_THOIGIAN, '%Y-%m-%d %H:%i:%s.0') AS TGKH_THOIGIAN,\r\n"
			+ "    AVG(tt.TGKH_GIA) AS gia\r\n"
			+ "FROM tour t\r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID = tt.T_ID  \r\n"
			+ "WHERE tt.TGKH_THOIGIAN > NOW() \r\n"
			+ "AND t.LT_ID = :id\r\n"
			+ "GROUP BY t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH, TGKH_THOIGIAN",nativeQuery = true)
	public List<Map<Object, Object>> getListTourFilter(int id);
}
