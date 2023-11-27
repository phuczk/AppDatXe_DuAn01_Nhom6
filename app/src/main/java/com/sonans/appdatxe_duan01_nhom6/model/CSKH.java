package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.HashMap;

public class CSKH {
    private String maCSKH;
    public String noiDung;
    public String sdt;
    private String maKH;
    public int trangThai;

    public CSKH() {
    }

    public CSKH(String maCSKH, String noiDung, String sdt, int trangThai) {
        this.maCSKH = maCSKH;
        this.noiDung = noiDung;
        this.sdt = sdt;
        this.trangThai = trangThai;
    }

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> cskh = new HashMap<>();
        cskh.put("maCSKH", maCSKH);
        cskh.put("noiDung", noiDung);
        cskh.put("sdt", sdt);
        cskh.put("trangThai", trangThai);
        return cskh;
    }

    public String getMaCSKH() {
        return maCSKH;
    }

    public void setMaCSKH(String maCSKH) {
        this.maCSKH = maCSKH;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
