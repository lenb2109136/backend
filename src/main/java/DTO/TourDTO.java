package DTO;

import java.util.List;

import com.example.hethongthuongmaidientu.model.CHAN;
import com.example.hethongthuongmaidientu.model.PhanHoi;
import com.example.hethongthuongmaidientu.model.Tour;

public class TourDTO {
	private Tour tour;
	private List<PhanHoi> phanHoi;
	private List<CHAN> chan;
	public Tour getTour() {
		return tour;
	}
	public void setTour(Tour tour) {
		this.tour = tour;
	}
	public List<PhanHoi> getPhanHoi() {
		return phanHoi;
	}
	public void setPhanHoi(List<PhanHoi> phanHoi) {
		this.phanHoi = phanHoi;
	}
	public List<CHAN> getChan() {
		return chan;
	}
	public void setChan(List<CHAN> chan) {
		this.chan = chan;
	}
	
}
