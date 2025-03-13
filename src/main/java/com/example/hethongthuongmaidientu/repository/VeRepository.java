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
}
