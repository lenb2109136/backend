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
@Table(name = "GIAUUDAI")
public class GiaUuDai {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GUD_ID")
	private int id;

	@Column(name = "GUD_GIA")
	private Float gia;

	@Column(name = "GUD_NGAPAPDUNG")
	private LocalDateTime ngayGioApDung;

	@Column(name = "GUD_NGAYKETTHUC")
	private LocalDateTime ngayKetThuc;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TGKH_ID")
	private ThoiGianKhoiHanh thoiGianKhoiHanhl;

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

	public LocalDateTime getNgayGioApDung() {
		return ngayGioApDung;
	}

	public void setNgayGioApDung(LocalDateTime ngayGioApDung) {
		this.ngayGioApDung = ngayGioApDung;
	}

	public LocalDateTime getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public ThoiGianKhoiHanh getThoiGianKhoiHanhl() {
		return thoiGianKhoiHanhl;
	}

	public void setThoiGianKhoiHanhl(ThoiGianKhoiHanh thoiGianKhoiHanhl) {
		this.thoiGianKhoiHanhl = thoiGianKhoiHanhl;
	}

}
