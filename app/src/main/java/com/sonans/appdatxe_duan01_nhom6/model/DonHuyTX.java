package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.Date;
import java.util.HashMap;

public class DonHuyTX {
    private String maDonHuy;
    public Date ngayKhoiHanh;
    public String diemBatDau;
    public String diemDen;
    private String maKhachDat;
    private String maTaiXe;

    public DonHuyTX() {
    }

    public DonHuyTX(String maDonHuy, Date ngayKhoiHanh, String diemBatDau, String diemDen, String maKhachDat, String maTaiXe) {
        this.maDonHuy = maDonHuy;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.diemBatDau = diemBatDau;
        this.diemDen = diemDen;
        this.maKhachDat = maKhachDat;
        this.maTaiXe = maTaiXe;
    }

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> donHuy = new HashMap<>();
        donHuy.put("maDonHuy", maDonHuy);
        donHuy.put("maKhachDat", maKhachDat);
        donHuy.put("maTaiXe", maTaiXe);
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

    public String getMaTaiXe() {
        return maTaiXe;
    }

    public void setMaTaiXe(String maTaiXe) {
        this.maTaiXe = maTaiXe;
    }
}
