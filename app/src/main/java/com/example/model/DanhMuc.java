package com.example.model;

import java.io.Serializable;

public class DanhMuc implements Serializable {
    private int maDanhMuc;
    private String tenDanhMuc;
    private String hinhDanhMuc;

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getHinhDanhMuc() {
        return hinhDanhMuc;
    }

    public void setHinhDanhMuc(String hinhDanhMuc) {
        this.hinhDanhMuc = hinhDanhMuc;
    }

    public DanhMuc() {
    }

    public DanhMuc(int maDanhMuc, String tenDanhMuc, String hinhDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.hinhDanhMuc = hinhDanhMuc;
    }
}
