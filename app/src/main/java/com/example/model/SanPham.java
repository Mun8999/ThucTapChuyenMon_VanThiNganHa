package com.example.model;

public class SanPham {
    private int maSp;
    private String tenSp;
    private String hinhSp;
    private int gia;
    private int danhGia;
    private int maLoaiSp;

    public SanPham() {
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

    public int getMaLoaiSp() {
        return maLoaiSp;
    }

    public void setMaLoaiSp(int maLoaiSp) {
        this.maLoaiSp = maLoaiSp;
    }
}
