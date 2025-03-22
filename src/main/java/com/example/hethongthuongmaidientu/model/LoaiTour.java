package com.example.hethongthuongmaidientu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "LOAITOUR")
@Entity
public class LoaiTour {
    @Id
    @Column(name = "LT_ID")
    private int id;

    @Column(name = "LT_TEN")
    private String ten;
    
    @Column(name = "LT_ICON")
    private String icon;

    // Constructor mặc định
    public LoaiTour() {
    }

    // Constructor có tham số
    public LoaiTour(String ten, String icon) {
        this.ten = ten;
        this.icon = icon;
    }

    // Getters và setters
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    @Override
    public String toString() {
        return "LoaiTour{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}