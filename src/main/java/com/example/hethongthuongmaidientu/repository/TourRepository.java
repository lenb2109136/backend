package com.example.hethongthuongmaidientu.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.CHAN;
import com.example.hethongthuongmaidientu.model.Response;
import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
	@Query(value = "\r\n"
			+ "SELECT \r\n"
			+ "    t.T_ID, \r\n"
			+ "    t.T_ANH, \r\n"
			+ "    t.T_TEN, \r\n"
			+ "    t.T_SONGAY, \r\n"
			+ "    t.T_SODEM, \r\n"
			+ "    t.T_SONGUOITHAMGIA, \r\n"
			+ "    t.T_SOSAO, \r\n"
			+ "    COUNT(p.PH_ID) AS soluongdanhg, \r\n"
			+ "    AVG(tt.TGKH_GIA) AS gia \r\n"
			+ "FROM tour t\r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID = tt.T_ID \r\n"
			+ "LEFT JOIN phanhoi p ON p.T_ID = t.T_ID \r\n"
			+ "GROUP BY \r\n"
			+ "    t.T_ID, t.T_ANH, t.T_TEN, t.T_SONGAY, t.T_SODEM, \r\n"
			+ "    t.T_SONGUOITHAMGIA, t.T_SOSAO\r\n"
			+ "LIMIT 6;", nativeQuery = true)
	public List<Map<Object, Object>> getHomeTour();

	@Query(value = "SELECT t.*\r\n"
			+ "	FROM \r\n"
			+ "	    tour t\r\n"
			+ "	JOIN \r\n"
			+ "	    thoigiankhoihanh tt ON t.T_ID = tt.T_ID\r\n"
			+ "	LEFT JOIN \r\n"
			+ "	    phanhoi p ON p.T_ID = t.T_ID\r\n"
			+ "	GROUP BY \r\n"
			+ "	    t.T_ID\r\n", nativeQuery = true)
	public List<Tour> getadmintour();

	@Query(value = "SELECT * FROM tour WHERE LT_ID=:id", nativeQuery = true)
	public List<Tour> getalllis(int id);

	@Query(value = """
			    SELECT count(*)
			    FROM thoigiankhoihanh p
			    JOIN tour t ON p.T_ID = t.T_ID
			    WHERE p.NV_ID = :nhanVienId
			    AND DATE_ADD(p.TGKH_THOIGIAN, INTERVAL t.T_SONGAY DAY)
			    BETWEEN :startDate AND :endDate
			""", nativeQuery = true)
	Integer findThoigianKetThuc(
			@Param("nhanVienId") Integer nhanVienId,
			@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	@Query("select p from ThoiGianKhoiHanh p where p.nhanVien.id=:nhanVienId")
	List<ThoiGianKhoiHanh> getThoiGianKhoiHanhByNhanVien(@Param("nhanVienId") Integer nhanVienId);

	@Query("select p from ThoiGianKhoiHanh p where p.nhanVien.id=:nhanVienId and p not in :l")
	List<ThoiGianKhoiHanh> getThoiGianKhoiHanhByNhanVien(@Param("nhanVienId") Integer nhanVienId,
			@Param("l") List<ThoiGianKhoiHanh> thoiGianKhoiHanh);

	@Query(value = "SELECT " +
	        "    t.T_ID, " +
	        "    t.T_TEN, " +
	        "    t.T_SONGAY, " +
	        "    t.T_SODEM, " +
	        "    t.T_ANH, " +
	        "    MAX(tt.TGKH_GIA) AS gia " + 
	        "FROM " +
	        "    tour t " +
	        "JOIN " +
	        "    thoigiankhoihanh tt ON t.T_ID = tt.T_ID " +
	        "GROUP BY " +
	        "    t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH", 
	        nativeQuery = true)
	public List<Map<Object, Object>> getListTour();

	
	@Query(value = "SELECT " +
	        "    t.T_ID, " +
	        "    t.T_TEN, " +
	        "    t.T_SONGAY, " +
	        "    t.T_SODEM, " +
	        "    t.T_ANH, " +
	        "    AVG(tt.TGKH_GIA) AS gia " +
	        "FROM " +
	        "    tour t " +
	        "JOIN " +
	        "    thoigiankhoihanh tt ON t.T_ID = tt.T_ID " +
	        "WHERE " +
	        "    t.LT_ID = :lt " +  // Điều kiện lọc loại tour
	        "GROUP BY " +
	        "    t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH", 
	        nativeQuery = true)
	public List<Map<Object, Object>> getListTourByLoai(int lt);

	@Query(value = "SELECT t.T_ID,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_ANH,AVG(tt.TGKH_GIA) as gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID  WHERE LT_ID=:lt GROUP BY t.T_ID ", nativeQuery = true)
	public List<Map<Object, Object>> getListTour(int lt);

	@Query(value = "SELECT " +
	        "    t.T_ID, " +
	        "    t.T_TEN, " +
	        "    t.T_SONGAY, " +
	        "    t.T_SODEM, " +
	        "    t.T_ANH, " +
	        "    DATE_FORMAT(MIN(tt.TGKH_THOIGIAN), '%Y-%m-%d %H:%i:%s.0') AS TGKH_THOIGIAN, " +
	        "    AVG(tt.TGKH_GIA) AS gia " +
	        "FROM tour t " +
	        "JOIN thoigiankhoihanh tt ON t.T_ID = tt.T_ID " +
	        "WHERE t.LT_ID = :id " +
	        "GROUP BY t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH",
	        nativeQuery = true)
	public List<Map<Object, Object>> getListTourFilterByLT(int id);
	@Query(value = "SELECT " +
	        "    t.T_ID, " +
	        "    t.T_TEN, " +
	        "    t.T_SONGAY, " +
	        "    t.T_SODEM, " +
	        "    t.T_ANH, " +
	        "    DATE_FORMAT(MIN(tt.TGKH_THOIGIAN), '%Y-%m-%d %H:%i:%s.0') AS TGKH_THOIGIAN, " +
	        "    AVG(tt.TGKH_GIA) AS gia " +
	        "FROM tour t " +
	        "JOIN thoigiankhoihanh tt ON t.T_ID = tt.T_ID " +
	        "GROUP BY t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH",
	        nativeQuery = true)
	public List<Map<Object, Object>> getListTourFilterBYyNotLoai();


	@Modifying
	@Query("delete from ThoiGianKhoiHanh p where p not in:l and p.tour=:tour")
	public void deleteThoiGianKhoiHanhNotInList(@Param("l") List<ThoiGianKhoiHanh> l, @Param("tour") Tour tour);

	@Modifying
	@Query("delete from CHAN p where p not in:l and p.tour=:tour")
	public void deleteChanNotInList(@Param("l") List<CHAN> l, @Param("tour") Tour tour);
	
	@Query(value = "SELECT t.* FROM tour t JOIN loaitour l ON t.LT_ID = l.LT_ID WHERE t.T_TEN LIKE CONCAT('%', :ten, '%') AND l.LT_ID = :idloai", nativeQuery = true)
	public List<Tour> getListTourr(String ten,int idloai );
	
	@Query(value = "SELECT GROUP_CONCAT(DISTINCT value ORDER BY value ASC SEPARATOR ', ') AS unique_tags\r\n"
			+ "FROM (\r\n"
			+ "    SELECT TRIM(value) AS value\r\n"
			+ "    FROM tour\r\n"
			+ "    JOIN (\r\n"
			+ "        SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(T_TAGS, ',', numbers.n), ',', -1) AS value\r\n"
			+ "        FROM (SELECT 1 AS n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) numbers\r\n"
			+ "        JOIN tour ON CHAR_LENGTH(T_TAGS) - CHAR_LENGTH(REPLACE(T_TAGS, ',', '')) >= numbers.n - 1\r\n"
			+ "    ) AS split_tags\r\n"
			+ ") AS tag_list;\r\n"
			+ "",nativeQuery = true)
	public String getTags();
}
