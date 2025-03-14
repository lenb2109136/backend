package com.example.hethongthuongmaidientu.model;

import java.security.PrivateKey;
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
@Table(name = "VE")
public class VE {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "V_ID")
	private int id;

	@Column(name = "V_GIA")
	private float gia;

	@ManyToOne
	@JoinColumn(name = "KH_ID")
	private KhachHang khachHang;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "TGKH_ID")
	private ThoiGianKhoiHanh thoiGianKhoiHanh;

	@Column(name = "V_NGAYDAT")
	private LocalDateTime ngayDat;
	
	
	@OneToMany(mappedBy = "ve")
	private List<PhiDichVu> phiDichVu;
	
	
	

	public List<PhiDichVu> getPhiDichVu() {
		return phiDichVu;
	}

	public void setPhiDichVu(List<PhiDichVu> phiDichVu) {
		this.phiDichVu = phiDichVu;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public LocalDateTime getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(LocalDateTime ngayDat) {
		this.ngayDat = ngayDat;
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
