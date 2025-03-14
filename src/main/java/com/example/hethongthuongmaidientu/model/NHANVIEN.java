package com.example.hethongthuongmaidientu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "NHANVIEN")
public class NHANVIEN {
		public static int idnv=0; 
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  	@Column(name="NV_ID")
	    private int id;

	    @Column(name = "NV_TEN")
	    private String ten;

	    @Column(name = "NV_ANH")
	    private String anh;

	    @Column(name = "NV_SDT")
	    private String soDienThoai;

	    @Column(name = "NV_SOCMND")
	    private String socmnd;

	    @JsonIgnore
	    @Column(name = "NV_MATKHAU")
	    private String matKhau;
	    
	    
		public String getSoDienThoai() {
			return soDienThoai;
		}

		public void setSoDienThoai(String soDienThoai) {
			this.soDienThoai = soDienThoai;
		}

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

		public String getTen() {
			return ten;
		}

		public void setTen(String ten) {
			this.ten = ten;
		}

		public String getAnh() {
			return anh;
		}

		public void setAnh(String anh) {
			this.anh = anh;
		}

		public String getSdt() {
			return soDienThoai;
		}

		public void setSdt(String sdt) {
			this.soDienThoai = sdt;
		}

		public String getSocmnd() {
			return socmnd;
		}

		public void setSocmnd(String socmnd) {
			this.socmnd = socmnd;
		}
	    
	    
	    
}
