package DTO;

import java.util.List;

import com.example.hethongthuongmaidientu.model.ThoiGianKhoiHanh;
import com.example.hethongthuongmaidientu.model.Tour;
import com.example.hethongthuongmaidientu.model.VE;

public class VEDTO2 {
		public VE ve;
		public ThoiGianKhoiHanh thoiGianKhoiHanh;
		public Tour tour;
		
		
		public Tour getTour() {
			return tour;
		}
		public void setTour(Tour tour) {
			this.tour = tour;
		}
		public VE getVe() {
			return ve;
		}
		public void setVe(VE ve) {
			this.ve = ve;
		}
		public ThoiGianKhoiHanh getThoiGianKhoiHanh() {
			return thoiGianKhoiHanh;
		}
		public void setThoiGianKhoiHanh(ThoiGianKhoiHanh thoiGianKhoiHanh) {
			this.thoiGianKhoiHanh = thoiGianKhoiHanh;
		}
		
		
}
