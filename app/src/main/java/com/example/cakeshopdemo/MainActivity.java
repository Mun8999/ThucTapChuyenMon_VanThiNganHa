package com.example.cakeshopdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapter.DanhMucAdapter;
import com.example.model.DanhMuc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvSanPham;
    ArrayList<DanhMuc> dsDanhMuc;
    DanhMucAdapter danhMucAdapter;
    RecyclerView rcvDanhMuc;
    int id=0;
    String hinhDanhMuc="";
    String tenDanhMuc="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dsDanhMuc=new ArrayList<>();
        getData();
        addViews();
    }

    private void addViews() {
        rcvDanhMuc=findViewById(R.id.rcvDanhMuc);
        lvSanPham=findViewById(R.id.lvSanPham);
        danhMucAdapter=new DanhMucAdapter(this,dsDanhMuc);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcvDanhMuc.setLayoutManager(linearLayoutManager);
        rcvDanhMuc.setAdapter(danhMucAdapter);
    }

    private void getData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayReques=new JsonArrayRequest("http://192.168.1.137//server/GetDanhMuc.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (requestQueue != null) {
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("MaDanhMuc");
                            tenDanhMuc=jsonObject.getString("TenDanhMuc");
                            hinhDanhMuc=jsonObject.getString("HinhDanhMuc");
                            dsDanhMuc.add(new DanhMuc(id,tenDanhMuc,hinhDanhMuc));
                            danhMucAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
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
//    void getData(){
//        dsDanhMuc.add(new DanhMuc(1,"Donut","donut.png"));
//        dsDanhMuc.add(new DanhMuc(1,"Donut","donut.png"));
//        dsDanhMuc.add(new DanhMuc(1,"Donut","donut.png"));
//        dsDanhMuc.add(new DanhMuc(1,"Donut","donut.png"));
//
//    }
    private class JsonTask extends AsyncTask<String, String, JSONArray> {
    @Override
    protected JSONArray doInBackground(String... strings) {
        HttpURLConnection connection=null;
        BufferedReader reader=null;
        try {
            URL url=new URL(strings[0]);
            connection=(HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer=new StringBuffer();
            String line="";
            while ( (line=reader.readLine())!=null){
                buffer.append(line);
                Log.d("respon", ">"+line);
            }
            return new JSONArray(buffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
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
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        if(jsonArray!=null){
            try {
                Log.e("alo", "Ten "+jsonArray.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

}
