package com.example.hethongthuongmaidientu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DICHVU")
public class DichVu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DV_ID")
	private int id;

	@Column(name = "DV_TEN")
	private String ten;

	@Column(name = "DV_MOTA")
	private String moTa;

	@Column(name = "DV_GIA")
	private Float gia;

	@Column(name = "DV_ANH")
	private String anh;
	
	@Column(name = "DV_TAGS")
	private String tags;
	public DichVu(int id, String ten, String moTa, float gia, String anh, String tags) {
        this.id = id;
        this.ten = ten;
        this.moTa = moTa;
        this.gia = gia;
        this.anh = anh;
        this.tags = tags;
    }

		

	public DichVu() {
	}



	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
	}

	public void setGia(Float gia) {
		this.gia = gia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public float getGia() {
		return gia;
	}

	public void setGia(float gia) {
		this.gia = gia;
	}

}