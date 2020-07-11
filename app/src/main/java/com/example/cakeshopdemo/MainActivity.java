package com.example.cakeshopdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapter.DanhMucAdapter;
import com.example.adapter.SanPhamAdapter;
import com.example.model.DanhMuc;
import com.example.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ultil.getConnect;

public class MainActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    ListView lvSanPham;
    public static ArrayList<SanPham> dsSanPham;
    public static SanPhamAdapter sanPhamAdapter;
    public static int maSp = 0;
    public static String hinhSp;
    public static String tenSp;
    public static int gia = 0;
    public static int danhGia = 0;
    public static int slTon = 0;

    ArrayList<DanhMuc> dsDanhMuc;
    DanhMucAdapter danhMucAdapter;
    RecyclerView rcvDanhMuc;
    int maDanhMuc = 0;
    String hinhDanhMuc = "";
    String tenDanhMuc = "";

    public static TextView txtSoLuong_GioHang;
    public static int soLuongGioHang=0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        getDuLieuDanhMuc();
        getDuLieuSanPham();
        actionViewFliper();
        addEvents();
//        sharedPreferences=getSharedPreferences("soluong",soLuongGioHang);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("soluong",soLuongGioHang);
    }

    private void addEvents() {
        lvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "chuyen man hinh", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, chitietsanpham.class);
                SanPham sp = dsSanPham.get(position);
                intent.putExtra("sanpham", (Serializable) sp);
                startActivity(intent);
            }
        });

    }


    //    JsonTask jsonTask=new JsonTask();
//        jsonTask.execute("http://192.168.1.102/data/GetDanhMuc.php");
    private void actionViewFliper() {
        int[] hinh = {R.drawable.donuts, R.drawable.donuts2, R.drawable.donuts3};

        for (int i : hinh
        ) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(i);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void addViews() {
        rcvDanhMuc = findViewById(R.id.rcvDanhMuc);
        lvSanPham = findViewById(R.id.lvSanPham);
        viewFlipper = findViewById(R.id.vfpQuangCao);
        txtSoLuong_GioHang=findViewById(R.id.txtSoLuong_GioHang);
        //soLuongGioHang=sharedPreferences.getInt("soluong",soLuongGioHang);
        dsDanhMuc = new ArrayList<>();
        dsSanPham = new ArrayList<>();
        Toast.makeText(this, dsSanPham.size() + "", Toast.LENGTH_SHORT).show();
        danhMucAdapter = new DanhMucAdapter(this, dsDanhMuc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvDanhMuc.setLayoutManager(linearLayoutManager);
        rcvDanhMuc.setAdapter(danhMucAdapter);

        sanPhamAdapter = new SanPhamAdapter(this, dsSanPham);
        lvSanPham.setAdapter(sanPhamAdapter);
    }

    private void getDuLieuDanhMuc() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        getConnect connect = new getConnect();
        connect.setUrl("d");
        JsonArrayRequest jsonArrayReques = new JsonArrayRequest(connect.getUrl() + "GetDanhMuc.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (requestQueue != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            maDanhMuc = jsonObject.getInt("MaDanhMuc");
                            tenDanhMuc = jsonObject.getString("TenDanhMuc");
                            hinhDanhMuc = jsonObject.getString("HinhDanhMuc");
                            dsDanhMuc.add(new DanhMuc(maDanhMuc, tenDanhMuc, hinhDanhMuc));
                            danhMucAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayReques);
    }

    private void getDuLieuSanPham() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        getConnect connect = new getConnect();
        connect.setUrl("d");
        JsonArrayRequest jsonArrayReques = new JsonArrayRequest(connect.getUrl() + "GetSanPham.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (requestQueue != null) {
                    Log.i("hhh", "onResponse: co internet ");
                    Log.i("lengt", "" + response.length());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            maSp = jsonObject.getInt("MaSp");
                            tenSp = jsonObject.getString("TenSp");
                            hinhSp = jsonObject.getString("HinhSp");
                            gia = jsonObject.getInt("Gia");
                            danhGia = jsonObject.getInt("DanhGia");
                            Log.i("hhh", "onResponse: co internet ");
                            slTon=jsonObject.getInt("SoLuong");
                            dsSanPham.add(new SanPham(maSp, tenSp, hinhSp, gia, danhGia,slTon));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
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

    private class JsonTask extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    Log.d("respon", ">" + line);
                }
                return new JSONObject(buffer.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if (jsonObject != null) {
                //Log.e("alo", "Ten "+jsonArray.getString());
            }
        }

    }
    public static void rcvItemClick(DanhMuc danhMuc, final Context context){
        dsSanPham.clear();
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        getConnect connect=new getConnect();
        connect.setUrl("d");
        JsonArrayRequest jsonArrayReques=new JsonArrayRequest(connect.getUrl()+"GetSanPhamTheoDanhMuc.php?MaDanhMuc="+danhMuc.getMaDanhMuc(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (requestQueue != null) {
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            maSp=jsonObject.getInt("MaSp");
                            tenSp=jsonObject.getString("TenSp");
                            hinhSp=jsonObject.getString("HinhSp");
                            gia=jsonObject.getInt("Gia");
                            danhGia=jsonObject.getInt("DanhGia");
                            Log.i("hhh", "onResponse: co internet ");
                            // Log.e("hinh :",hinhSp );
                            slTon=jsonObject.getInt("SoLuong");
                            dsSanPham.add(new SanPham(maSp,tenSp,hinhSp,gia,danhGia,slTon));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    Toast.makeText(context, "Không có kết nối internet", Toast.LENGTH_LONG).show();
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
    public static int getSoLuongGioHang(){
        Log.e(">", "main getSoLuongGioHang: "+soLuongGioHang);
        soLuongGioHang=Integer.parseInt(txtSoLuong_GioHang.getText().toString());
        return soLuongGioHang;
    }
    public static void thayDoiSoLuongGioHang(int sl){
        Log.e(">", "main getSoLuong thay doi: "+sl);
        if (sl>=0)
        txtSoLuong_GioHang.setText(sl+"");
    }
//    public static void getDuLieuSanPhamTheoDanhMuc() {
//        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        getConnect connect=new getConnect();
//        connect.setHost("192.168.1.114");
//        connect.setUrl("d");
//        JsonArrayRequest jsonArrayReques=new JsonArrayRequest(connect.getUrl()+"GetSanPhamTheoDanhMuc.php?MaDanhMuc"+maDanhMuc, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (requestQueue != null) {
//                    for (int i=0;i<response.length();i++){
//                        try {
//                            JSONObject jsonObject=response.getJSONObject(i);
//                            maSp=jsonObject.getInt("MaSp");
//                            tenSp=jsonObject.getString("TenSp");
//                            hinhSp=jsonObject.getString("HinhSp");
//                            gia=jsonObject.getInt("Gia");
//                            danhGia=jsonObject.getInt("DanhGia");
//                            Log.i("hhh", "onResponse: co internet ");
//                            // Log.e("hinh :",hinhSp );
//                            // slTon=jsonObject.getInt("SoLuong");
//                            dsSanPham.add(new SanPham(maSp,tenSp,hinhSp,gia,danhGia));
//                            sanPhamAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }else {
//                    Toast.makeText(MainActivity.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
//                }
//                //Toast.makeText(MainActivity.this, dsSanPham.size()+"", Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayReques);
//    }
}
