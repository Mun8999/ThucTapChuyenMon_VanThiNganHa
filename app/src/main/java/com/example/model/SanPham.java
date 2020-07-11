package com.example.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int maSp;
    private String tenSp;
    private String hinhSp;
    private int gia;
    private int danhGia;
    private String maLoaiSp;
    private int soLuongMua;
    private int soLuongTon;

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public SanPham() {
    }

    public SanPham(int maSp, String tenSp, String hinhSp, int gia, int danhGia, int soLuongTon) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.hinhSp = hinhSp;
        this.gia = gia;
        this.danhGia = danhGia;
        this.soLuongTon = soLuongTon;
    }

    public SanPham(int maSp, String tenSp, String hinhSp, int gia, int danhGia) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.hinhSp = hinhSp;
        this.gia = gia;
        this.danhGia = danhGia;
    }

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

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }

    public String getMaLoaiSp() {
        return maLoaiSp;
    }

    public void setMaLoaiSp(String maLoaiSp) {
        this.maLoaiSp = maLoaiSp;
    }
}
