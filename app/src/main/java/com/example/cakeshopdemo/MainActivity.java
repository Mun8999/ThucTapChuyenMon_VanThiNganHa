package com.example.cakeshopdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.adapter.GioHangAdapter;
import com.example.adapter.SanPhamAdapter;
import com.example.giohang.Databases;
import com.example.model.DanhMuc;
import com.example.model.GioHang;
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
    public static Databases databases;
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
    public static ArrayList<GioHang> dsGioHang;
    GioHangAdapter gioHangAdapter;

    ArrayList<DanhMuc> dsDanhMuc;
    DanhMucAdapter danhMucAdapter;
    RecyclerView rcvDanhMuc;
    int maDanhMuc = 0;
    String hinhDanhMuc = "";
    String tenDanhMuc = "";

    public static TextView txtSoLuong_GioHang;
    public static int soLuongGioHang=0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
        PreparedDB();
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
        txtSoLuong_GioHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                soLuongGioHang=Integer.parseInt(txtSoLuong_GioHang.getText().toString());
                Toast.makeText(MainActivity.this, "textchange"+soLuongGioHang, Toast.LENGTH_LONG).show();
                sharedPreferences=getSharedPreferences("dulieu",MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.putInt("soluonggiohang",soLuongGioHang);
                editor.commit();
                Toast.makeText(MainActivity.this, "share preferences"+sharedPreferences.getInt("ha",0), Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //int t=sharedPreferences.getInt("ha",0);
        //txtSoLuong_GioHang.setText(t+"");
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
        sharedPreferences=getSharedPreferences("dulieu",MODE_PRIVATE);
        soLuongGioHang=sharedPreferences.getInt("soluonggiohang",0);
        txtSoLuong_GioHang.setText(soLuongGioHang+"");

        dsDanhMuc = new ArrayList<>();
        dsSanPham = new ArrayList<>();
        dsGioHang=new ArrayList<>();
        Toast.makeText(this, dsSanPham.size() + "", Toast.LENGTH_SHORT).show();
        danhMucAdapter = new DanhMucAdapter(this, dsDanhMuc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvDanhMuc.setLayoutManager(linearLayoutManager);
        rcvDanhMuc.setAdapter(danhMucAdapter);

        sanPhamAdapter = new SanPhamAdapter(this, dsSanPham);
        lvSanPham.setAdapter(sanPhamAdapter);
    }
    private void PreparedDB() {
        databases =new Databases(this,"giohang.sqlite",null,1);

        databases.QueryData("CREATE TABLE IF NOT EXISTS GioHang (MaSp INTEGER PRIMARY KEY AUTOINCREMENT, "+" TenSp VARCHAR(200),"
                                +" HinhSp varchar(200),"+" GiaSp integer,"+" SoLuong integer)");
//        databases.QueryData("insert into Works values (null, 'Fix bugs')");
//        databases.QueryData("insert into Works values (null, 'Coding')");
//        databases.QueryData("insert into Works values (null, 'Meeting')");
//        databases.QueryData("insert into Works values (null, 'Walking')");
    }
    public static void getSanPhamGioHang(int soluong) {
        Cursor cursor=databases.GetData("select * from GioHang");
        dsGioHang.clear();
        int maSp;
        String tenSp;
        String hinhSp;
        int gia;
        while (cursor.moveToNext()){
            maSp=cursor.getInt(0);
            tenSp=cursor.getString(1);
            hinhSp=cursor.getString(2);
            gia=cursor.getInt(3);
            soluong=cursor.getInt(4);
            //Log.i("=====soluongsanpham", ""+soluong);
            dsGioHang.add(new GioHang(maSp,tenSp,hinhSp,gia,soluong));
            Log.e("SanPham", "> MaSp: "+maSp+"> TenSp: "+tenSp+"> HinhSp: "+hinhSp+"> GiaSp: "+gia+"> SoLuong: "+soluong);
        }
    }
    public static void getDanhSachGioHang() {
        for (GioHang sp: dsGioHang
             ) {
            Log.d("SanPham", "> MaSp: "+sp.getMaSp()+"> TenSp: "+sp.getTenSp()+"> HinhSp: "+sp.getHinhSp()+"> GiaSp: "+sp.getGia()+"> SoLuong: "+sp.getSoLuongMua());
        }
    }

     void getDuLieuDanhMuc() {
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

    public static void themSanPham(SanPham sp,int soluong){
        databases.QueryData("insert into GioHang values (null,'"+sp.getTenSp()+"','"+sp.getHinhSp()+"','"+sp.getGia()+"','"+soluong+"')");
    }
    public static void suaSanPham(SanPham sp,int soluong){
        databases.QueryData("update GioHang set TenSp='"+sp.getTenSp()+"',HinhSp='"+sp.getHinhSp()+"',GiaSp='"+sp.getGia()+"',SoLuong='"+soluong+"' where MaSp='"+sp.getMaSp()+"'");
    }
    public static void xoaSanPham(SanPham sp){
        databases.GetData("delete from GioHang where MaSp='"+sp.getMaSp()+"'");
    }
    public static void thayDoiSoLuongGioHang(int slSanPham,int slGioHang,SanPham sp){
        Log.d("aloalaooalaoao", "<<<<  "+slGioHang);
        if (slGioHang>=0) {
            txtSoLuong_GioHang.setText(slGioHang + "");
            if (slSanPham == 1) {
                themSanPham(sp,slSanPham);
            }
            if(slSanPham>1){
                suaSanPham(sp,slSanPham);
            }
        }else {
            xoaSanPham(sp);
        }

        Log.i("-----SO LUONG", slSanPham+"" );
        getSanPhamGioHang(slSanPham);
        getDanhSachGioHang();
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
