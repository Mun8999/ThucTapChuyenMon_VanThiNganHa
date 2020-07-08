package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cakeshopdemo.R;
import com.example.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ultil.getConnect;

public class SanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> dsSanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> dsSanPham) {
        this.context = context;
        this.dsSanPham = dsSanPham;
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
        TextView txtTenSanPham,txtDanhGia,txtGia;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_sanpham,null);
            viewHolder.imvSanPham=convertView.findViewById(R.id.imvSanPham);
            viewHolder.txtTenSanPham=convertView.findViewById(R.id.txtTenSanPham);
            viewHolder.txtGia=convertView.findViewById(R.id.txtGiaSanPham);
            viewHolder.txtDanhGia=convertView.findViewById(R.id.txtDanhGia);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        SanPham sp=dsSanPham.get(position);
        getConnect connect=new getConnect();
        connect.setHost("192.168.1.102");
        connect.setUrl("sp");
        Picasso.get()
                .load(connect.getUrl()+sp.getHinhSp())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(viewHolder.imvSanPham);
        Log.e("URL :",connect.getUrl()+sp.getHinhSp() );
        viewHolder.txtTenSanPham.setText(sp.getTenSp());
        viewHolder.txtGia.setText(sp.getGia()+"");
        viewHolder.txtDanhGia.setText(sp.getDanhGia()+"");
        return convertView;
    }
}
