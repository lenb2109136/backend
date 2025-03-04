package com.example.hethongthuongmaidientu.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PHANHOI")
public class PhanHoi {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PH_ID")
    private int id;

    @Column(name = "PH_THOIGIANPHANHOI")
    private LocalDateTime thoiGianPhanHoi;

    @Column(name = "PH_NOIDUNGPHANHOI")
    private String noiDungPhanHoi;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "PHA_PH_ID ")
    private PhanHoi reply;
    
    @ManyToOne
    @JoinColumn(name = "KH_ID")
    private KhachHang khachHang;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "T_ID")
    private Tour tour;
    
    

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getThoiGianPhanHoi() {
		return thoiGianPhanHoi;
	}

	public void setThoiGianPhanHoi(LocalDateTime thoiGianPhanHoi) {
		this.thoiGianPhanHoi = thoiGianPhanHoi;
	}

	public String getNoiDungPhanHoi() {
		return noiDungPhanHoi;
	}

	public void setNoiDungPhanHoi(String noiDungPhanHoi) {
		this.noiDungPhanHoi = noiDungPhanHoi;
	}

	public PhanHoi getReply() {
		return reply;
	}

	public void setReply(PhanHoi reply) {
		this.reply = reply;
	}
    
    
}
