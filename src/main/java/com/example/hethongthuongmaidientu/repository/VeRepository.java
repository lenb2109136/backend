package com.example.hethongthuongmaidientu.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hethongthuongmaidientu.model.VE;

@Repository
public interface VeRepository extends JpaRepository<VE, Integer> {
    @Query(value = "SELECT v.v_id,tt.T_TEN,v.V_GIA,v.V_NGAYDAT,tt.T_THOIGIANKHOIHANH  FROM ve v JOIN thoigiankhoihanh t ON v.TGKH_ID=t.TGKH_ID JOIN tour tt ON t.T_ID=tt.T_ID\r\n"
            + "WHERE v.KH_ID=:id AND v.V_NGAYDAT >=:nbt and v.V_NGAYDAT<=:kt", nativeQuery = true)
    public List<Map<Object, Object>> getve(
            int id,
            LocalDateTime nbt,
            LocalDateTime kt);

    @Query(value = "SELECT v.* \r\n"
            + "FROM ve v \r\n"
            + "JOIN thoigiankhoihanh t ON v.TGKH_ID = t.TGKH_ID \r\n"
            + "JOIN tour tt ON tt.T_ID = t.T_ID \r\n"
            + "WHERE t.TGKH_THOIGIAN > NOW() AND t.T_ID=:id", nativeQuery = true)
    public List<VE> gettrung();

    @Query(value = "SELECT * FROM ve WHERE KH_ID=:idkh", nativeQuery = true)
    public List<VE> getVeByKhachHang(int idkh);

    @Query(value = "SELECT * \r\n"
            + "FROM ve \r\n"
            + "WHERE KH_ID = :id\r\n"
            + "ORDER BY V_NGAYDAT DESC \r\n"
            + "LIMIT 1;\r\n"
            + "", nativeQuery = true)
    public VE getvenew(int id);
}
