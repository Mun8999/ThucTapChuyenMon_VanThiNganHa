package com.example.model;

import java.io.Serializable;

public class GioHang implements Serializable {
    private int maSp;
    private String tenSp;
    private String hinhSp;
    private int gia;
    private int soLuongMua;

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getHinhSp() {
        return hinhSp;
    }

    public void setHinhSp(String hinhSp) {
        this.hinhSp = hinhSp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public GioHang(int maSp, String tenSp, String hinhSp, int gia, int soLuongMua) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.hinhSp = hinhSp;
        this.gia = gia;
        this.soLuongMua = soLuongMua;
    }

}
