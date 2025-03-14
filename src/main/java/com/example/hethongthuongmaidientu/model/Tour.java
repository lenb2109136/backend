package com.example.hethongthuongmaidientu.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@NotBlank(message = "Vui lòng nhập n=tên tour")
	private String ten;

	@Column(name = "T_ANH")
	private String anh;
	
	@Column(name = "T_TAGS")
	private String tags;
	
	
	
	@ManyToOne
	@NotNull(message = "Chưa chọn loại tour")
	@JoinColumn(name = "LT_ID")
	private LoaiTour loaiTour;

	@ManyToOne
	@JoinColumn(name = "NV_ID")
	private NHANVIEN nhanvien;

	@Valid
	@Size(min = 1, message = "Ít nhất một thời gian khởi hanghf")
	@OneToMany(mappedBy = "tour")
	private List<ThoiGianKhoiHanh> thoiGianKhoiHanh2;

	@Valid
	@Size(min = 1, message = "Ít nhất một chặn cho một tour")
	@OneToMany(mappedBy = "tour")
	private List<CHAN> chan;

	//
	// public List<PhanHoi> getPhanHoi() {
	// return phanHoi;
	// }
	//
	// public void setPhanHoi(List<PhanHoi> phanHoi) {
	// this.phanHoi = phanHoi;
	// }
	
	
	

	public List<CHAN> getChan() {
		return chan;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<ThoiGianKhoiHanh> getThoiGianKhoiHanh2() {
		return thoiGianKhoiHanh2;
	}

	public void setThoiGianKhoiHanh2(List<ThoiGianKhoiHanh> thoiGianKhoiHanh2) {
		this.thoiGianKhoiHanh2 = thoiGianKhoiHanh2;
	}

	public void setChan(List<CHAN> chan) {
		this.chan = chan;
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

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
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

}
