package com.example.cakeshopdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapter.SanPhamAdapter;
import com.example.model.SanPham;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ultil.getConnect;

public class chitietsanpham extends AppCompatActivity {
    Button btnMua;
    ImageButton btnGioHang;
    ImageView imvSanPham;
    RatingBar rtDanhGia;
    TextView txtTenSanPham,txtGia,txtTenLoaiSp;
    ListView lvSanPham;
    String tenLoaiSp;
    ArrayList<SanPham> dsSanPham;
    SanPhamAdapter sanPhamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        addViews();
        //progressBarSLTon.setProgress(12);
        getDuLieuSanPham();
        addEvents();
    }

    private void addEvents() {
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue=Volley.newRequestQueue(chitietsanpham.this);
                StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://172.17.13.245/data/delete.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("thanhcong")){
                            Toast.makeText(chitietsanpham.this, "xoa thanh cong", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("MaDanhMuc","14");
                        return hashMap;

                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void addViews() {
        dsSanPham=new ArrayList<>();
        btnGioHang=findViewById(R.id.imvCart_ct);
        btnMua=findViewById(R.id.btnMua);
        imvSanPham=findViewById(R.id.imvSanPham_ct);
        rtDanhGia=findViewById(R.id.rtDanhGia_ct);
        txtTenSanPham=findViewById(R.id.txtTenSanPham_ct);
        txtGia=findViewById(R.id.txtGia_ct);
        lvSanPham=findViewById(R.id.lvSanPham_ct);
        txtTenLoaiSp=findViewById(R.id.txtLoaiSanPham_ct);
        sanPhamAdapter=new SanPhamAdapter(chitietsanpham.this,dsSanPham);
    }

    private void getDuLieuSanPham() {
        Intent intent=getIntent();
        SanPham sp= (SanPham) intent.getSerializableExtra("sanpham");
        getConnect connect=new getConnect();
        connect.setUrl("sp");
        txtTenSanPham.setText(sp.getTenSp());
        rtDanhGia.setRating(sp.getDanhGia());
        txtGia.setText(sp.getGia()+"");
        Picasso.get()
                .load(connect.getUrl()+sp.getHinhSp())
                .placeholder(R.drawable.loader)
                .error(R.drawable.noimage)
                .into(imvSanPham);
        getDuLieu(sp.getMaSp());
        //int soluong=sp.getSoLuongTon();
       // int percent=5*100/soluong;

    }
    private void getDuLieu(int masp) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        getConnect connect=new getConnect();
        connect.setUrl("d");
        JsonArrayRequest jsonArrayReques=new JsonArrayRequest(connect.getUrl()+"test.php?MaSp="+masp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (requestQueue != null) {
                    Log.i("hhh", "onResponse: co internet ");
                    Log.i("lengt", ""+response.length());
                    try {
                        JSONObject jsonObject=response.getJSONObject(0);
                        tenLoaiSp=jsonObject.getString("TenLoaiSp");
                        txtTenLoaiSp.setText(tenLoaiSp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                }
                //Toast.makeText(MainActivity.this, dsSanPham.size()+"", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayReques);

    }
}
