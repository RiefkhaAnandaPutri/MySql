package com.example.mysql;

import  androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//import com.example.sqlite.adapter.TemanAdapter;
//import com.example.sqlite.database.DBcontroller;
//import com.example.sqlite.database.Teman;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TemanAdapter adapter;
    private ArrayList<Teman> temanArrayList = new ArrayList<>();
    //DBcontroller controler = new DBcontroller(this);
    String id,nm,tlp;
    private FloatingActionButton fab;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static String url_select = "http://10.0.2.2/umyTI/bacateman.php";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_TELPON = "telpon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingBtn);
        BacaData();
        adapter = new TemanAdapter(temanArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this,TemanBaru.class);
                //startActivity(intent);
            }
        });

    }

    public  void BacaData(){
        requestQueue requestQueue = Volley.
                newRequestQueue(getApplicationContext());

        JSONArrayRequest jArr = new JSONArrayRequest(
                url_select,new Response.Listener<JSONArray>() {
                    @Override
            public void onResponse(JSONArray response
                    ) {
                        Log.d(TAG, response.toString());

                        //Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Teman item = new Teman();
                                item.setId(obj.getString(TAG_ID));
                                item.setId(obj.getString(TAG_NAMA));
                                item.setId(obj.getString(TAG_TELPON));

                                //Menambah item ke array
                                temanArrayList.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
            public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this,"gagal",Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jArr);
    }
}