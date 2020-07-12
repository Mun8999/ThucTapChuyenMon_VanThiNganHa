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
import com.example.model.SanPham;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import ultil.getConnect;

public class SanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> dsSanPham;
    static int sl=0;
    static int slGioHang=0;

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
        TextView txtTenSanPham,txtDanhGia,txtGia,txtSoLuong;
        Button btnAdd,btnDelete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_sanpham,null);
            viewHolder.imvSanPham=convertView.findViewById(R.id.imvSanPham);
            viewHolder.txtTenSanPham=convertView.findViewById(R.id.txtTenSanPham);
            viewHolder.txtGia=convertView.findViewById(R.id.txtGiaSanPham);
            viewHolder.txtDanhGia=convertView.findViewById(R.id.txtDanhGia);
            viewHolder.txtSoLuong=convertView.findViewById(R.id.txtSoLuong);
            viewHolder.btnAdd=convertView.findViewById(R.id.btnAdd);
            viewHolder.btnDelete=convertView.findViewById(R.id.btnRemove);
            //viewHolder.txtSoLuong_GioHang=convertView.findViewById(R.id.txtSoLuong_GioHang);
            viewHolder.txtSoLuong.setText("0");
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final SanPham sp=dsSanPham.get(position);
        getConnect connect=new getConnect();
        connect.setUrl("sp");
        Picasso.get()
                .load(connect.getUrl()+sp.getHinhSp())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(viewHolder.imvSanPham);
        viewHolder.txtTenSanPham.setText(sp.getTenSp());
        viewHolder.txtGia.setText(sp.getGia()+"");
        viewHolder.txtDanhGia.setText(sp.getDanhGia()+"");
        final SharedPreferences preferences = this.context.getSharedPreferences("SanPham",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl = Integer.parseInt(viewHolder.txtSoLuong.getText().toString());
                sl++;
                editor.putInt(sp.getMaSp()+"",sl);
                editor.commit();
                viewHolder.txtSoLuong.setText(sl+"");
                slGioHang=MainActivity.getSoLuongGioHang();
                slGioHang++;
                editor.putInt("soLuongGioHang",slGioHang);
                editor.commit();
                MainActivity.thayDoiSoLuongGioHang(sl,slGioHang,dsSanPham.get(position));
                Log.e("SanPham :",viewHolder.txtSoLuong.getText().toString() );
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl = Integer.parseInt(viewHolder.txtSoLuong.getText().toString());
                sl--;
                if(sl<0)
                    sl=0;
                editor.putInt(sp.getMaSp()+"",sl);
                viewHolder.txtSoLuong.setText(sl+"");
                editor.commit();
                slGioHang=MainActivity.getSoLuongGioHang();
                slGioHang--;
                MainActivity.thayDoiSoLuongGioHang(sl,slGioHang,dsSanPham.get(position));
            }
        });
        int soluong = preferences.getInt(sp.getMaSp()+"",0);
        if(soluong!=0){
            viewHolder.txtSoLuong.setText(soluong+"");
        }
        else
            viewHolder.txtSoLuong.setText("0");
        return convertView;
    }
}
