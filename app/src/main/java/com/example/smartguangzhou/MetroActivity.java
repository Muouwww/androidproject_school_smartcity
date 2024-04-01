package com.example.smartguangzhou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.smartguangzhou.Adapter.RvMetroLineAdapter;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoMetroLine;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MetroActivity extends AppCompatActivity {
    TextView stationA,stationB,metroTime,metroKM;
    RecyclerView rvMetroLine;

    public String lineId ="";
    OkHttpClient client = new OkHttpClient();


    List<PojoMetroLine.Data.MetroStepList> mstepData = new ArrayList<>();

    public String runStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_metro);
        initView();
        initEvent();
    }

    private void initEvent() {
        showInvariablePoint(lineId);
    }

    private void initView() {
        lineId = String.valueOf(getIntent().getIntExtra("lineId",1));
        stationA = findViewById(R.id.metrostationa);
        stationB = findViewById(R.id.metrostationb);
        metroTime = findViewById(R.id.metroreachtime);
        metroKM = findViewById(R.id.metrokm);

        rvMetroLine = findViewById(R.id.rv_metroLine);

        CardView cardView = findViewById(R.id.cardView4);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MetroActivity.this,MetromapActivity.class));
            }
        });
    }

    public void initRvMetroLine(){
        RvMetroLineAdapter rvMetroLineAdapter = new RvMetroLineAdapter(MetroActivity.this,mstepData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MetroActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvMetroLine.setLayoutManager(linearLayoutManager);
        rvMetroLine.setAdapter(rvMetroLineAdapter);
        rvMetroLineAdapter.setData(mstepData,runStation);
    }

    public void showInvariablePoint(String linesId){
        String url = Constant.IP + Constant.METROLINE;
        Request request = new Request.Builder()
                .url(url+linesId)
                .build();
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
                        String strJson = response.body().string();
                        if (response.isSuccessful()){
                            PojoMetroLine pojoMetroLine = new Gson().fromJson(strJson, PojoMetroLine.class);
                            mstepData = pojoMetroLine.getData().getMetroStepList();
                            runStation = pojoMetroLine.getData().getRunStationsName();

//                            Log.d("s sss",mData.get(0).getRunStationsName());

                            String startStation = pojoMetroLine.getData().getFirst();
                            String endStation = pojoMetroLine.getData().getEnd();
                            String carTime = pojoMetroLine.getData().getRemainingTime()+"分钟";
                            String carSpeed = pojoMetroLine.getData().getKm()+"km/h";

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    stationA.setText(startStation);
                                    stationB.setText(endStation);
                                    metroTime.setText(carTime);
                                    metroKM.setText(carSpeed);
                                    initRvMetroLine();
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }
}