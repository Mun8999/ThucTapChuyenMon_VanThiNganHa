package com.example.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cakeshopdemo.MainActivity;
import com.example.cakeshopdemo.R;
import com.example.model.GioHang;
import com.example.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ultil.getConnect;

public class GioHangAdapter extends BaseAdapter {
    ArrayList<GioHang> dsSanPham;
    Context context;

    public GioHangAdapter(ArrayList<GioHang> dsSanPham, Context context) {
        this.dsSanPham = dsSanPham;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dsSanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return dsSanPham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        ImageView imvSanPham;
        TextView txtTenSanPham,txtGia,txtSoLuong;
        Button btnAdd,btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final ViewHolder viewHolder;
       if(convertView==null){

       }
       return convertView;
    }
}
