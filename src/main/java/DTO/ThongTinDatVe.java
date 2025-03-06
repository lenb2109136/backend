package DTO;

import java.util.List;

public class ThongTinDatVe {
	private Integer idkh;
	private List<VeDTO> infove;
	public Integer getIdkh() {
		return idkh;
	}
	public void setIdkh(Integer idkh) {
		this.idkh = idkh;
	}
	public List<VeDTO> getInfove() {
		return infove;
	}
	public void setInfove(List<VeDTO> infove) {
		this.infove = infove;
	}
	
}
