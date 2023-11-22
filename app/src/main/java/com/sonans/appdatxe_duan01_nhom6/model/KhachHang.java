package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.HashMap;

public class KhachHang {
    private String maKhachHang;
    public String tenKhachHang;
    private String soDT;
    private String tenDangNhap;
    private String matKhau;


    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> khachHang = new HashMap<>();
        khachHang.put("maKhachHang", maKhachHang);
        khachHang.put("tenKhachHang", tenKhachHang);
        khachHang.put("soDT", soDT);
        khachHang.put("tenDangNhap", tenDangNhap);
        khachHang.put("matKhau", matKhau);
        return khachHang;
    }
    public KhachHang() {
    }

    public KhachHang(String maKhachHang, String tenKhachHang, String soDT, String tenDangNhap, String matKhau) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDT = soDT;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
