package com.example.smartguangzhou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoResult;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FeedActivity extends AppCompatActivity {

    private TextInputLayout editFeedback;
    private Button btnSubmit;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        
        initView();
        initEvent();
    }

    private void initView() {
        editFeedback = (TextInputLayout) findViewById(R.id.edit_feedback);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private void initEvent() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editFeedback.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(info)){
                    Toast.makeText(FeedActivity.this, "反馈意见内容不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                postFeed(info);
            }
        });
    }
    public void postFeed(String info){
        String url = Constant.IP  + Constant.ADD_FEEDBACK_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("content", info);
        map.put("title", "发现错误");
        String json = new Gson().toJson(map);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json;charset=UTF-8"));
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization",Constant.TOKEN)
                .build();
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
                                PojoResult pojoResult = new
                                        Gson().fromJson(strJson, PojoResult.class);
                                if (pojoResult.getCode() == 200) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FeedActivity.this,"提交成功", Toast.LENGTH_SHORT).show();
                                            editFeedback.getEditText().setText("");
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FeedActivity.this,pojoResult.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}