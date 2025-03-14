package com.example.hethongthuongmaidientu.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.CHAN;
import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
	@Query(value = "SELECT t.T_ID,t.T_ANH,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_SONGUOITHAMGIA,t.T_SOSAO, COUNT(p.PH_ID) AS soluongdanhg,AVG(tt.TGKH_GIA) AS gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID LEFT JOIN phanhoi p ON p.T_ID=t.T_ID WHERE tt.TGKH_THOIGIAN>NOW() GROUP BY t.T_ID LIMIT 6", nativeQuery = true)
	public List<Map<Object, Object>> getHomeTour();

	@Query(value = "SELECT t.*\r\n"
			+ "	FROM \r\n"
			+ "	    tour t\r\n"
			+ "	JOIN \r\n"
			+ "	    thoigiankhoihanh tt ON t.T_ID = tt.T_ID\r\n"
			+ "	LEFT JOIN \r\n"
			+ "	    phanhoi p ON p.T_ID = t.T_ID\r\n"
			+ "	WHERE \r\n"
			+ "	    tt.TGKH_THOIGIAN > NOW()\r\n"
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

	@Query(value = "SELECT t.T_ID,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_ANH,AVG(tt.TGKH_GIA) as gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID  WHERE tt.TGKH_THOIGIAN>NOW() GROUP BY t.T_ID ", nativeQuery = true)
	public List<Map<Object, Object>> getListTour();

	@Query(value = "SELECT t.T_ID,t.T_TEN,t.T_SONGAY,t.T_SODEM,t.T_ANH,AVG(tt.TGKH_GIA) as gia FROM tour t \r\n"
			+ "JOIN thoigiankhoihanh tt ON t.T_ID=tt.T_ID  WHERE tt.TGKH_THOIGIAN>NOW() AND LT_ID=:lt GROUP BY t.T_ID ", nativeQuery = true)
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
			+ "WHERE tt.TGKH_THOIGIAN > NOW() \r\n"
			+ "AND t.LT_ID = :id AND (t.T_TEN like %:ten% OR t.T_MOTA like %:ten%) \r\n"
			+ "GROUP BY t.T_ID, t.T_TEN, t.T_SONGAY, t.T_SODEM, t.T_ANH, TGKH_THOIGIAN", nativeQuery = true)
	public List<Map<Object, Object>> getListTourFilter(int id, String ten);

	@Modifying
	@Query("delete from ThoiGianKhoiHanh p where p not in:l and p.tour=:tour")
	public void deleteThoiGianKhoiHanhNotInList(@Param("l") List<ThoiGianKhoiHanh> l, @Param("tour") Tour tour);

	@Modifying
	@Query("delete from CHAN p where p not in:l and p.tour=:tour")
	public void deleteChanNotInList(@Param("l") List<CHAN> l, @Param("tour") Tour tour);
	
	@Query(value = "SELECT t.* FROM tour t JOIN loaitour l ON t.LT_ID = l.LT_ID WHERE t.T_TEN LIKE CONCAT('%', :ten, '%') AND l.LT_ID = :idloai", nativeQuery = true)
	public List<Tour> getListTourr(String ten,int idloai );


}
