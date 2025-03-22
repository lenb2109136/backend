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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
//22/3 ngon
@Table(name = "TOUR")
@Entity
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "T_ID")
    private int id;

    @Column(name = "T_THOIGIANKHOIHANH")
    private LocalDateTime thoiGianKhoiHanh;

    @Column(name = "T_SONGAY")
    @Min(value = 1, message = "Số ngày không được bé hơn 1")
    private Integer soNgay;

    @Column(name = "T_SODEM")
    @Min(value = 1, message = "Số đêm không được bé hơn 1")
    private Integer soDem;

    @Column(name = "T_SONGUOITHAMGIA")
    @Min(value = 1, message = "Số người tham gia không được bé hơn 1")
    private Integer soNguoiThamGia;

    @Column(name = "T_MOTA")
    private String moTa;

    @Column(name = "T_TEN")
    @NotBlank(message = "Vui lòng nhập tên tour")
    private String ten;

    @Column(name = "T_ANH")
    private String anh;

    @Column(name = "T_TAGS")
    private String tags;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Chưa chọn loại tour")
    @JoinColumn(name = "LT_ID")
    private LoaiTour loaiTour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NV_ID")
    private NHANVIEN nhanvien;

    @JsonIgnore // Prevent serialization to avoid circular reference
    @Valid
    @Size(min = 1, message = "Ít nhất một thời gian khởi hành")
    @OneToMany(mappedBy = "tour")
    private List<ThoiGianKhoiHanh> thoiGianKhoiHanh2;

    @Valid
    @Size(min = 1, message = "Ít nhất một chặng cho một tour")
    @OneToMany(mappedBy = "tour")
    private List<CHAN> chan;

    // Getters and Setters
    public List<CHAN> getChan() {
        return chan;
    }

    public void setChan(List<CHAN> chan) {
        this.chan = chan;
    }

    public List<ThoiGianKhoiHanh> getThoiGianKhoiHanh2() {
        return thoiGianKhoiHanh2;
    }

    public void setThoiGianKhoiHanh2(List<ThoiGianKhoiHanh> thoiGianKhoiHanh2) {
        this.thoiGianKhoiHanh2 = thoiGianKhoiHanh2;
    }

    public LoaiTour getLoaiTour() {
        return loaiTour;
    }

    public void setLoaiTour(LoaiTour loaiTour) {
        this.loaiTour = loaiTour;
    }

    public NHANVIEN getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(NHANVIEN nhanvien) {
        this.nhanvien = nhanvien;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getThoiGianKhoiHanh() {
        return thoiGianKhoiHanh;
    }

    public void setThoiGianKhoiHanh(LocalDateTime thoiGianKhoiHanh) {
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public Integer getSoNgay() {
        return soNgay;
    }

    public void setSoNgay(Integer soNgay) {
        this.soNgay = soNgay;
    }

    public Integer getSoDem() {
        return soDem;
    }

    public void setSoDem(Integer soDem) {
        this.soDem = soDem;
    }

    public Integer getSoNguoiThamGia() {
        return soNguoiThamGia;
    }

    public void setSoNguoiThamGia(Integer soNguoiThamGia) {
        this.soNguoiThamGia = soNguoiThamGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}