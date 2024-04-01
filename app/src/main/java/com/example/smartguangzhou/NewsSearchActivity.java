package com.example.smartguangzhou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smartguangzhou.Adapter.RvTypeNewsAdapter;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoNews;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsSearchActivity extends AppCompatActivity {

    private RecyclerView rvNews;

    String strQuery ="";
    OkHttpClient client = new OkHttpClient();
    public List<PojoNews.RowsNewsPojo> rowsNewsPojo_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);

        initView();
//        initEvent();
        getQuery();
        getSearchNewsData(strQuery);
    }

    private void initView(){
        rvNews = (RecyclerView) findViewById(R.id.rv_news);
    }
    private void initEvent(){

    }

    private void getQuery(){
        Intent it = getIntent();
        strQuery = it.getStringExtra("query");
    }

    public void getSearchNewsData(String sName) {
        String url = Constant.IP + Constant.NEWS_URL;
        String params = "?title=" + strQuery;
        Request request = new Request.Builder().url(url + params).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response)
                                throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoNews pojoNews = new Gson().fromJson(strJson,
                                        PojoNews.class);
                                rowsNewsPojo_List = pojoNews.getRows();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (rowsNewsPojo_List.size() != 0) {
                                            initRVNews();
                                        } else {
                                            Toast.makeText(NewsSearchActivity.this, "找不到相关服务，请重新输入", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void initRVNews(){
        RvTypeNewsAdapter rvTypeNewsAdapter = new RvTypeNewsAdapter(NewsSearchActivity.this, null);
        rvNews.setLayoutManager(new LinearLayoutManager(NewsSearchActivity.this,RecyclerView.VERTICAL,false));
        rvNews.addItemDecoration(new DividerItemDecoration(NewsSearchActivity.this,RecyclerView.VERTICAL));
        rvNews.setAdapter(rvTypeNewsAdapter);
        rvTypeNewsAdapter.setData(rowsNewsPojo_List);
    }
}