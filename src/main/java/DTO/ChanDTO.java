package DTO;

import java.time.LocalDate;

public class ChanDTO {
    private Integer id; // Đổi thành Integer để khớp với kiểu int của model
    private Integer tId; // Lấy T_ID từ mối quan hệ ManyToOne với Tour
    private String diaDiemDen; // Đổi tên cDiaDiemDen thành diaDiemDen để phù hợp với tên biến trong model
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String moTa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTId() {
        return tId;
    }

    public void setTId(Integer tId) {
        this.tId = tId;
    }

    public String getDiaDiemDen() {
        return diaDiemDen;
    }

    public void setDiaDiemDen(String diaDiemDen) {
        this.diaDiemDen = diaDiemDen;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}