package com.example.hethongthuongmaidientu.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
//22/3 ngon
@Entity
@Table(name = "ve")
public class VE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "V_ID")
    private int id;

    @Column(name = "V_GIA")
    private float gia;

    @Column(name = "V_NGAYDAT")
    private LocalDateTime ngayDat;

    @ManyToOne(fetch = FetchType.EAGER) // Fetch eagerly
    @JoinColumn(name = "KH_ID")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.EAGER) // Fetch eagerly
    @JoinColumn(name = "TGKH_ID")
    private ThoiGianKhoiHanh thoiGianKhoiHanh;

    @OneToMany(mappedBy = "ve", cascade = CascadeType.ALL)
    private List<PhiDichVu> phiDichVu;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public ThoiGianKhoiHanh getThoiGianKhoiHanh() {
        return thoiGianKhoiHanh;
    }

    public void setThoiGianKhoiHanh(ThoiGianKhoiHanh thoiGianKhoiHanh) {
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public List<PhiDichVu> getPhiDichVu() {
        return phiDichVu;
    }

    public void setPhiDichVu(List<PhiDichVu> phiDichVu) {
        this.phiDichVu = phiDichVu;
    }
}