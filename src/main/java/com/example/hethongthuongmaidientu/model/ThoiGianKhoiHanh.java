package com.example.hethongthuongmaidientu.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "THOIGIANKHOIHANH")
public class ThoiGianKhoiHanh {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TGKH_ID")
    private int id;
	
	@Column(name = "TGKH_THOIGIAN")
	private LocalDateTime thoiGian;
	
	@Column(name = "TGKH_GIA")
	private float gia;
	
	@OneToMany(mappedBy = "thoiGianKhoiHanhl")
	private List<GiaUuDai> giaUuDai;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "T_ID")
	private Tour tour;
	
	
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
