package com.example.smartguangzhou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoRegister;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegActivity extends AppCompatActivity {


    TextInputEditText user,pass,nickname,phonenumber;

    Button register;

    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        initView();
        initEvent();
    }

    private void initEvent() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUser = user.getText().toString();
                String sPass = pass.getText().toString();
                String sNickname = nickname.getText().toString();
                String sPhonenumber = phonenumber.getText().toString();
                if (!TextUtils.isEmpty(sUser) || !TextUtils.isEmpty(sPass) ||!TextUtils.isEmpty(sNickname) ||!TextUtils.isEmpty(sPhonenumber) ){
                    postRegister(sUser,sPass,sNickname,sPhonenumber);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
                    builder.setTitle("注册失败").setMessage("填写信息不能为空！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    public void postRegister(String user,String pass,String nickName,String phoneNumber){

        Map<String,String>maps = new HashMap<>();
        maps.put("userName",user);
        maps.put("nickName",nickName);
        maps.put("password",pass);
        maps.put("phonenumber",phoneNumber);
        maps.put("sex","0");
        String json = new Gson().toJson(maps);
        Log.i("json：",json);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json;charset=UTF-8"));
        final Request request = new Request.Builder()
                .url(Constant.IP + Constant.REGISTER)
                .post(requestBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()){
                                PojoRegister pojoRegister = new Gson().fromJson(strJson, PojoRegister.class);
                                if (pojoRegister.getCode() == 200){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
                                            builder.setTitle("注册完成").setMessage("注册成功，即将跳转登录界面")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            startActivity(new Intent(RegActivity.this,LoginActivity.class));
                                                            finish();
                                                        }
                                                    });
                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.show();
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegActivity.this);
                                            builder.setTitle("注册失败").setMessage(pojoRegister.getMsg())
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                        }
                                                    });
                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        user = findViewById(R.id.reg_user);
        pass = findViewById(R.id.reg_pass);
        nickname = findViewById(R.id.reg_nickname);
        phonenumber = findViewById(R.id.reg_phone);

        register = findViewById(R.id.reg_bt);
    }
}