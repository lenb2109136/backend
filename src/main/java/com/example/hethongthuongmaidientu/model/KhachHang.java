package com.example.hethongthuongmaidientu.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "KHACHHANG")
public class KhachHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KH_ID")
	private int id;

	@NotBlank(message = "Số điện thoại không được để trống")
	@Pattern(regexp = "^(\\+84|0)[3|5|7|8|9][0-9]{8}$", message = "Số điện thoại không hợp lệ")
	@Column(name = "KH_SDT")
	private String soDienThoai;

	@Column(name = "KH_TEN")
	private String ten;

	@NotBlank(message = "CCCD không được để trống")
	@Pattern(regexp = "^[0-9]{12}$", message = "CCCD phải có 12 chữ số")
	@Column(name = "KH_CCCD")
	private String cccd;

	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không đúng định dạng")
	@Column(name = "KH_EMAIL")
	private String email;

	@Column(name = "KH_GIOITIINH")
	private boolean gioiTinh;

	@Column(name = "KH_DIACHI")
	private String diaChi;

	@Column(name = "KH_NAMSINH")
	private LocalDate namSinh;

	@JsonIgnore
	@Column(name = "KH_MATKHAU")
	private String matKhau;

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public LocalDate getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(LocalDate namSinh) {
		this.namSinh = namSinh;
	}

}
