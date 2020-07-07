package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cakeshopdemo.R;
import com.example.model.DanhMuc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder>{
    private Context context;
    private ArrayList<DanhMuc> dsDanhMuc;

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> dsDanhMuc) {
        this.context = context;
        this.dsDanhMuc = dsDanhMuc;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_danhmuc,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc danhMuc=dsDanhMuc.get(position);
        Picasso.get()
                .load("http://192.168.1.137/img/image/danhmuc/white/"+danhMuc.getHinhDanhMuc())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(holder.imv);
        holder.txt.setText(danhMuc.getTenDanhMuc());
    }

    @Override
    public int getItemCount() {
        return dsDanhMuc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imv;
        TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv=itemView.findViewById(R.id.imvDanhMuc);
            txt=itemView.findViewById(R.id.txtDanhMuc);
        }
    }

}
