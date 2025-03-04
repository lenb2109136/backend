package com.example.hethongthuongmaidientu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LICHSUTIMKIEM")
public class LICHSUTIMKIEM {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LSTK_ID")
    private int id;

    @Column(name = "LSTK_NOIDUNGTIMKIEM", length = 255)
    private String noiDungTimKiem;
    
    @ManyToOne
    @JoinColumn(name = "KH_ID")
    private KhachHang khachHang;
    
    

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNoiDungTimKiem() {
		return noiDungTimKiem;
	}

	public void setNoiDungTimKiem(String noiDungTimKiem) {
		this.noiDungTimKiem = noiDungTimKiem;
	}
    
    
}
