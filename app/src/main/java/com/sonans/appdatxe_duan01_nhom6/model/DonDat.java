package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.Date;
import java.util.HashMap;

public class DonDat {
    private String maDonDat;
    public Date ngayKhoiHanh;
    public String diemBatDau;
    public String diemDen;
    private String maKhachDat;
    public String tenKhachHang;
    public String sdtKhachHang;
    public int soLuongKhach;
    public int giaCuoc;

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> donDat = new HashMap<>();
        donDat.put("maDonDat", maDonDat);
        donDat.put("maKhachDat", maKhachDat);
        donDat.put("tenKhachHang", tenKhachHang);
        donDat.put("sdtKhachHang", sdtKhachHang);
        donDat.put("diemBatDau", diemBatDau);
        donDat.put("diemDen", diemDen);
        donDat.put("ngayKhoiHanh", ngayKhoiHanh);
        donDat.put("soLuongKhach", soLuongKhach);
        donDat.put("giaCuoc", giaCuoc);
        return donDat;
    }

    public DonDat() {
    }

    public DonDat(String maDonDat, Date ngayKhoiHanh, String diemBatDau, String diemDen, String maKhachDat, String tenKhachHang, String sdtKhachHang, int soLuongKhach, int giaCuoc) {
        this.maDonDat = maDonDat;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.diemBatDau = diemBatDau;
        this.diemDen = diemDen;
        this.maKhachDat = maKhachDat;
        this.tenKhachHang = tenKhachHang;
        this.sdtKhachHang = sdtKhachHang;
        this.soLuongKhach = soLuongKhach;
        this.giaCuoc = giaCuoc;
    }

    public String getMaDonDat() {
        return maDonDat;
    }

    public void setMaDonDat(String maDonDat) {
        this.maDonDat = maDonDat;
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

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSdtKhachHang() {
        return sdtKhachHang;
    }

    public void setSdtKhachHang(String sdtKhachHang) {
        this.sdtKhachHang = sdtKhachHang;
    }

    public int getSoLuongKhach() {
        return soLuongKhach;
    }

    public void setSoLuongKhach(int soLuongKhach) {
        this.soLuongKhach = soLuongKhach;
    }

    public int getGiaCuoc() {
        return giaCuoc;
    }

    public void setGiaCuoc(int giaCuoc) {
        this.giaCuoc = giaCuoc;
    }
}
