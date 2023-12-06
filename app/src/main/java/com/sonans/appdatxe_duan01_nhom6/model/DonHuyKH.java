package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.Date;
import java.util.HashMap;

public class DonHuyKH {
    private String maDonHuy;
    public Date ngayKhoiHanh;
    public String diemBatDau;
    public String diemDen;
    private String maKhachDat;

    public DonHuyKH() {
    }

    public DonHuyKH(String maDonHuy, Date ngayKhoiHanh, String diemBatDau, String diemDen, String maKhachDat) {
        this.maDonHuy = maDonHuy;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.diemBatDau = diemBatDau;
        this.diemDen = diemDen;
        this.maKhachDat = maKhachDat;
    }

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> donHuy = new HashMap<>();
        donHuy.put("maDonHuy", maDonHuy);
        donHuy.put("maKhachDat", maKhachDat);
        donHuy.put("diemBatDau", diemBatDau);
        donHuy.put("diemDen", diemDen);
        donHuy.put("ngayKhoiHanh", ngayKhoiHanh);
        return donHuy;
    }
    public String getMaDonHuy() {
        return maDonHuy;
    }

    public void setMaDonHuy(String maDonHuy) {
        this.maDonHuy = maDonHuy;
    }

    public Date getNgayKhoiHanh() {
        return ngayKhoiHanh;
    }

    public void setNgayKhoiHanh(Date ngayKhoiHanh) {
        this.ngayKhoiHanh = ngayKhoiHanh;
    }

    public String getDiemBatDau() {
        return diemBatDau;
    }

    public void setDiemBatDau(String diemBatDau) {
        this.diemBatDau = diemBatDau;
    }

    public String getDiemDen() {
        return diemDen;
    }

    public void setDiemDen(String diemDen) {
        this.diemDen = diemDen;
    }

    public String getMaKhachDat() {
        return maKhachDat;
    }

    public void setMaKhachDat(String maKhachDat) {
        this.maKhachDat = maKhachDat;
    }
}
