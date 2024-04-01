package com.example.smartguangzhou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoMap;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebmapActivity extends AppCompatActivity {

    WebView mwebView;
    String weburl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webmap);

        mwebView = findViewById(R.id.webMap);

        initEvent();
    }

    public void showWeb(String url){
        mwebView.loadUrl(url);
        mwebView.getSettings().setBuiltInZoomControls(true);
    }
    private void initEvent() {
        String url = Constant.IP + "/prod-api/api/metro/city";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()){
                            String strJson = response.body().string();
                            PojoMap maps = new Gson().fromJson(strJson, PojoMap.class);
                            String Weburl = Constant.IP+maps.data.imgUrl;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showWeb(Weburl);
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }
}