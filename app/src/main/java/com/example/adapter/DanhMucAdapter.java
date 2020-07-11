package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cakeshopdemo.MainActivity;
import com.example.cakeshopdemo.R;
import com.example.model.DanhMuc;
import com.example.model.SanPham;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ultil.getConnect;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder>{
    int maSp=0;
    String hinhSp;
    String tenSp;
    int gia=0;
    int danhGia=0;
    int slTon=0;
    int loaiSp;
    ListView lvSanPham;
    ArrayList<SanPham> dsSanPham;
    SanPhamAdapter sanPhamAdapter;
    private Context context;
    private ArrayList<DanhMuc> dsDanhMuc;
    void addView(){
        dsSanPham=new ArrayList<>();

    }
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
        getConnect connect=new getConnect();
        connect.setUrl("dmw");
        Picasso.get()
                .load(connect.getUrl()+danhMuc.getHinhDanhMuc())
                .placeholder(R.drawable.loading)
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.rcvItemClick(dsDanhMuc.get(getAdapterPosition()),context);
                }
            });
        }
    }


}
