package com.example.hethongthuongmaidientu.model;

import java.security.PrivateKey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	    @JoinColumn(name = "TGKH_ID")
	    private ThoiGianKhoiHanh thoiGianKhoiHanh;
	    
	    

		public KhachHang getKhachHang() {
			return khachHang;
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
