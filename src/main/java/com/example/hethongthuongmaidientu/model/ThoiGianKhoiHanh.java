package com.example.hethongthuongmaidientu.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
//22/3 ngon
@Entity
@Table(name = "THOIGIANKHOIHANH")
public class ThoiGianKhoiHanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TGKH_ID")
    private Integer id;

    @Column(name = "TGKH_THOIGIAN")
    private LocalDateTime thoiGian;

    @Column(name = "TGKH_GIA")
    private float gia;

    @NotNull(message = "Chưa chọn nhân viên")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NV_ID")
    private NHANVIEN nhanVien;

    @JsonIgnore // Prevent serialization of the ve list to break the circular reference
    @OneToMany(mappedBy = "thoiGianKhoiHanh")
    private List<VE> ve;

    @OneToMany(mappedBy = "thoiGianKhoiHanhl")
    private List<GiaUuDai> giaUuDai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_ID")
    private Tour tour;

    // Getters and Setters
    public NHANVIEN getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NHANVIEN nhanVien) {
        this.nhanVien = nhanVien;
    }

    public List<VE> getVe() {
        return ve;
    }

    public void setVe(List<VE> ve) {
        this.ve = ve;
    }

    public List<GiaUuDai> getGiaUuDai() {
        return giaUuDai;
    }

    public void setGiaUuDai(List<GiaUuDai> giaUuDai) {
        this.giaUuDai = giaUuDai;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
}