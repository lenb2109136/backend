package com.example.hethongthuongmaidientu.model;

import java.time.LocalDate;
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
import jakarta.validation.constraints.NotBlank;

@Table(name = "CHAN")
@Entity
public class CHAN {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_ID")
	private int id;

	@NotBlank(message = "Vui lòng nhập tên địa điểm đến")
	@Column(name = "C_DIADIEMDEN")
	private String DiaDiemDen;

	@Column(name = "C_MOTA")
	private String moTa;

	@Column(name = "C_NGAYBATDAU")
	private LocalDate ngayBatDau;

	@Column(name = "C_NGAYKETTHUC")
	private LocalDate ngayKetThuc;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "T_ID")
	private Tour tour;

	public int getId() {
		return id;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDiaDiemDen() {
		return DiaDiemDen;
	}

	public void setDiaDiemDen(String diaDiemDen) {
		DiaDiemDen = diaDiemDen;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public LocalDate getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(LocalDate ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public LocalDate getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(LocalDate ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

}
