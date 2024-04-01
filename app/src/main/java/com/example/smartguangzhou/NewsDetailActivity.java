package com.example.smartguangzhou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoNewsDetail;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView tvNewsTitle;
    private TextView tvNewsTime;
    private ImageView imageNewsIcon;
    private WebView wvNewsView;

    int newsId = -1;
    OkHttpClient client = new OkHttpClient();
    PojoNewsDetail.DataPojo dataPojo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initView();
        initEvent();

        getId();
        getNewsDetailData();
    }

    private void initView(){
        tvNewsTitle = (TextView) findViewById(R.id.tv_news_title);
        tvNewsTime = (TextView) findViewById(R.id.tv_news_time);
        imageNewsIcon = (ImageView) findViewById(R.id.image_news_icon);
        wvNewsView = (WebView) findViewById(R.id.wv_news_view);

//        tvTitle.setText("新闻详情");
    }

    private void initEvent(){

    }

    private void getId(){
        Intent it = getIntent();
        newsId = it.getIntExtra("id",-1);
    }

    private void getNewsDetailData(){
        String url = Constant.IP + Constant.NEWS_DETAIL;
        String params = newsId + "";
        Request request = new Request.Builder().url(url+params).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }
                        @Override
                        public void onResponse(Call call, Response response)throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoNewsDetail pojoNewsDetail = new Gson().fromJson(strJson, PojoNewsDetail.class);
                                dataPojo = pojoNewsDetail.getData();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebViewNews();
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

    private void initWebViewNews(){
        tvNewsTime.setText(dataPojo.getCreateTime());
        tvNewsTitle.setText(dataPojo.getTitle());
        Glide.with(NewsDetailActivity.this)
                .load(Constant.IP+dataPojo.getCover())
                .into(imageNewsIcon);
        wvNewsView.loadDataWithBaseURL(Constant.IP,dataPojo.getContent(),"text/html","utf-8",null);
    }
}