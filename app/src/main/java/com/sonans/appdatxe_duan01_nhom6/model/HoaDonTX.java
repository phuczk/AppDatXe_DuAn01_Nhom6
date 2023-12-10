package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.Date;
import java.util.HashMap;

public class HoaDonTX {
    private String maHoaDon;
    public int soTien;
    public String thanhTien;
    public String thoiGian;
    public Date time;
    private String maTX;

    public HoaDonTX() {
    }

    public HoaDonTX(String maHoaDon, int soTien, String thanhTien, String thoiGian, Date time, String maTX) {
        this.maHoaDon = maHoaDon;
        this.soTien = soTien;
        this.thanhTien = thanhTien;
        this.thoiGian = thoiGian;
        this.time = time;
        this.maTX = maTX;
    }

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> hoaDon = new HashMap<>();
        hoaDon.put("maHoaDon", maHoaDon);
        hoaDon.put("soTien", soTien);
        hoaDon.put("thanhTien", thanhTien);
        hoaDon.put("thoiGian", thoiGian);
        hoaDon.put("time", time);
        hoaDon.put("maTX", maTX);
        return hoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMaTX() {
        return maTX;
    }

    public void setMaTX(String maTX) {
        this.maTX = maTX;
    }
}
