package com.example.hethongthuongmaidientu.model;

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
@Table(name = "PHIDICHVU")
public class PhiDichVu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PDV_ID")
	private int id;

	@Column(name = "PDV_GIA")
	private Float gia;

	@ManyToOne
	@JoinColumn(name = "DV_ID")
	private DichVu dichVu;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "V_ID")
	private VE ve;

	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	public VE getVe() {
		return ve;
	}

	public void setVe(VE ve) {
		this.ve = ve;
	}

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

}