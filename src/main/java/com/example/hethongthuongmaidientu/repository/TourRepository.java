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
	//ngôn 21/3
		// Tổng doanh thu từ vé (Earnings)
	    @Query(value = "SELECT SUM(v.V_GIA) as total FROM ve v", nativeQuery = true)
	    Double getTotalEarnings();

	    // Doanh thu tháng hiện tại (Sales - Tháng 3/2025)
	    @Query(value = "SELECT SUM(v.V_GIA) as total FROM ve v WHERE v.V_NGAYDAT BETWEEN :startDate AND :endDate", nativeQuery = true)
	    Double getMonthlySales(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	    // Số tour có ngày khởi hành trong tương lai (New Tasks)
	    @Query(value = "SELECT COUNT(*) as count FROM thoigiankhoihanh tgkh WHERE tgkh.TGKH_THOIGIAN > :currentDate", nativeQuery = true)
	    Integer getNewTasks(@Param("currentDate") LocalDateTime currentDate);

	    // Tổng số tour (Total Projects)
	    @Query(value = "SELECT COUNT(*) as count FROM tour", nativeQuery = true)
	    Integer getTotalProjects();
	    @Query(value = "SELECT MONTH(v.V_NGAYDAT) as month, SUM(v.V_GIA) as revenue " +
	    	       "FROM ve v WHERE YEAR(v.V_NGAYDAT) = 2025 " +
	    	       "GROUP BY MONTH(v.V_NGAYDAT)", nativeQuery = true)
	    	List<Map<String, Object>> getMonthlyRevenue();
	    @Query(value = "SELECT lt.LT_TEN as typeName, SUM(v.V_GIA) as revenue " +
	    	       "FROM ve v JOIN thoigiankhoihanh tgkh ON v.TGKH_ID = tgkh.TGKH_ID " +
	    	       "JOIN tour t ON tgkh.T_ID = t.T_ID JOIN loaitour lt ON t.LT_ID = lt.LT_ID " +
	    	       "GROUP BY lt.LT_ID, lt.LT_TEN", nativeQuery = true)
	    	List<Map<String, Object>> getTourTypeRevenue();
	    @Query(value = "SELECT MONTH(v.V_NGAYDAT) as month, COUNT(*) as tickets " +
	    	       "FROM ve v WHERE YEAR(v.V_NGAYDAT) = 2025 " +
	    	       "GROUP BY MONTH(v.V_NGAYDAT)", nativeQuery = true)
	    	List<Map<String, Object>> getTicketSales();
	    @Query(value = "SELECT COUNT(*) as count FROM khachhang", nativeQuery = true)
	    Integer getTotalCustomers();
	    @Query(value = "SELECT COUNT(*) as count FROM nhanvien", nativeQuery = true)
	    Integer getTotalEmployees();
	    @Query(value = "SELECT COUNT(*) as count FROM thoigiankhoihanh", nativeQuery = true)
	    Integer getTotalTrips();
	  //end ngôn 21/3
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

	@Query(value = "SELECT \r\n"
			+ "    t.T_ID, \r\n"
			+ "    t.T_TEN, \r\n"
			+ "    t.T_SONGAY, \r\n"
			+ "    t.T_SODEM, \r\n"
			+ "    t.T_ANH, \r\n"
			+ "    AVG(tt.TGKH_GIA) AS gia \r\n"
			+ "FROM \r\n"
			+ "    tour t\r\n"
			+ "JOIN \r\n"
			+ "    thoigiankhoihanh tt ON t.T_ID = tt.T_ID  \r\n"
			+ "GROUP BY \r\n"
			+ "    t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH;", nativeQuery = true)
	public List<Map<Object, Object>> getListTour();
	
	@Query(value = "SELECT \r\n"
			+ "    t.T_ID, \r\n"
			+ "    t.T_TEN, \r\n"
			+ "    t.T_SONGAY, \r\n"
			+ "    t.T_SODEM, \r\n"
			+ "    t.T_ANH, \r\n"
			+ "    AVG(tt.TGKH_GIA) AS gia \r\n"
			+ "FROM \r\n"
			+ "    tour t\r\n"
			+ "JOIN \r\n"
			+ "    thoigiankhoihanh tt ON t.T_ID = tt.T_ID where t.LT_ID=:lt  \r\n"
			+ "GROUP BY \r\n"
			+ "    t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH;", nativeQuery = true)
	public List<Map<Object, Object>> getListTourByLoai(int lt);

	@Query(value = "SELECT t.T_ID,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_ANH,AVG(tt.TGKH_GIA) as gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID  WHERE LT_ID=:lt GROUP BY t.T_ID ", nativeQuery = true)
	public List<Map<Object, Object>> getListTour(int lt);

	@Query(value = "SELECT DISTINCT \r\n"
			+ "    t.T_ID,\r\n"
			+ "    t.T_TEN,\r\n"
			+ "    t.T_SONGAY,\r\n"
			+ "    t.T_SODEM,\r\n"
			+ "    t.T_ANH,\r\n"
			+ "    DATE_FORMAT(tt.TGKH_THOIGIAN, '%Y-%m-%d %H:%i:%s.0') AS TGKH_THOIGIAN,\r\n"
			+ "    AVG(tt.TGKH_GIA) AS gia\r\n"
			+ "FROM tour t\r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID = tt.T_ID  \r\n"
			+ "where t.LT_ID = :id\r\n"
			+ "GROUP BY t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH, TGKH_THOIGIAN", nativeQuery = true)
	public List<Map<Object, Object>> getListTourFilterByLT(int id);
	@Query(value = "SELECT DISTINCT \r\n"
			+ "    t.T_ID,\r\n"
			+ "    t.T_TEN,\r\n"
			+ "    t.T_SONGAY,\r\n"
			+ "    t.T_SODEM,\r\n"
			+ "    t.T_ANH,\r\n"
			+ "    DATE_FORMAT(tt.TGKH_THOIGIAN, '%Y-%m-%d %H:%i:%s.0') AS TGKH_THOIGIAN,\r\n"
			+ "    AVG(tt.TGKH_GIA) AS gia\r\n"
			+ "FROM tour t\r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID = tt.T_ID  \r\n"
			+ "GROUP BY t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH, TGKH_THOIGIAN", nativeQuery = true)
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
