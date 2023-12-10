package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.Date;
import java.util.HashMap;

public class DonNhan {
    private String maDonNhan;
    public Date ngayKhoiHanh;
    public String diemBatDau;
    public String diemDen;
    private String maKhachDat;
    private String maTaiXe;
    public String tenKhachHang;
    public String sdtKhachHang;
    public int soLuongKhach;
    public int giaCuoc;

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> donNhan = new HashMap<>();
        donNhan.put("maDonNhan", maDonNhan);
        donNhan.put("maKhachDat", maKhachDat);
        donNhan.put("maTaiXe", maTaiXe);
        donNhan.put("tenKhachHang", tenKhachHang);
        donNhan.put("sdtKhachHang", sdtKhachHang);
        donNhan.put("diemBatDau", diemBatDau);
        donNhan.put("diemDen", diemDen);
        donNhan.put("ngayKhoiHanh", ngayKhoiHanh);
        donNhan.put("soLuongKhach", soLuongKhach);
        donNhan.put("giaCuoc", giaCuoc);
        return donNhan;
    }

    public DonNhan() {
    }

    public DonNhan(String maDonNhan, Date ngayKhoiHanh, String diemBatDau, String diemDen, String maKhachDat, String maTaiXe, String tenKhachHang, String sdtKhachHang, int soLuongKhach, int giaCuoc) {
        this.maDonNhan = maDonNhan;
        this.ngayKhoiHanh = ngayKhoiHanh;
        this.diemBatDau = diemBatDau;
        this.diemDen = diemDen;
        this.maKhachDat = maKhachDat;
        this.maTaiXe = maTaiXe;
        this.tenKhachHang = tenKhachHang;
        this.sdtKhachHang = sdtKhachHang;
        this.soLuongKhach = soLuongKhach;
        this.giaCuoc = giaCuoc;
    }

    public String getMaDonNhan() {
        return maDonNhan;
    }

    public void setMaDonNhan(String maDonNhan) {
        this.maDonNhan = maDonNhan;
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
