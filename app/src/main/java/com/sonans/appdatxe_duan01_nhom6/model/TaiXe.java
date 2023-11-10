package com.sonans.appdatxe_duan01_nhom6.model;

import java.util.HashMap;

public class TaiXe {
    private String maTaiXe;
    public String tenTaiXe;
    public int tuoiTaiXe;
    private String sdtTaiXe;
    private String tenDN_TaiXe;
    private String matKhauTaiXe;

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> taiXe = new HashMap<>();
        taiXe.put("maTaiXe", maTaiXe);
        taiXe.put("tenTaiXe", tenTaiXe);
        taiXe.put("tuoiTaiXe", tuoiTaiXe);
        taiXe.put("sdtTaiXe", sdtTaiXe);
        taiXe.put("tenDN_TaiXe", tenDN_TaiXe);
        taiXe.put("matKhauTaiXe", matKhauTaiXe);
        return taiXe;
    }

    public TaiXe() {
    }

    public TaiXe(String maTaiXe, String tenTaiXe, int tuoiTaiXe, String sdtTaiXe, String tenDN_TaiXe, String matKhauTaiXe) {
        this.maTaiXe = maTaiXe;
        this.tenTaiXe = tenTaiXe;
        this.tuoiTaiXe = tuoiTaiXe;
        this.sdtTaiXe = sdtTaiXe;
        this.tenDN_TaiXe = tenDN_TaiXe;
        this.matKhauTaiXe = matKhauTaiXe;
    }

    public String getMaTaiXe() {
        return maTaiXe;
    }

    public void setMaTaiXe(String maTaiXe) {
        this.maTaiXe = maTaiXe;
    }

    public String getTenTaiXe() {
        return tenTaiXe;
    }

    public void setTenTaiXe(String tenTaiXe) {
        this.tenTaiXe = tenTaiXe;
    }

    public int getTuoiTaiXe() {
        return tuoiTaiXe;
    }

    public void setTuoiTaiXe(int tuoiTaiXe) {
        this.tuoiTaiXe = tuoiTaiXe;
    }

    public String getSdtTaiXe() {
        return sdtTaiXe;
    }

    public void setSdtTaiXe(String sdtTaiXe) {
        this.sdtTaiXe = sdtTaiXe;
    }

    public String getTenDN_TaiXe() {
        return tenDN_TaiXe;
    }

    public void setTenDN_TaiXe(String tenDN_TaiXe) {
        this.tenDN_TaiXe = tenDN_TaiXe;
    }

    public String getMatKhauTaiXe() {
        return matKhauTaiXe;
    }

    public void setMatKhauTaiXe(String matKhauTaiXe) {
        this.matKhauTaiXe = matKhauTaiXe;
    }
}
