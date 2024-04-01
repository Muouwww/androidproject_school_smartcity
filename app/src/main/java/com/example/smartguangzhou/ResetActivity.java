package com.example.smartguangzhou;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoChange;
import com.google.android.material.textfield.TextInputEditText;
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

public class ResetActivity extends AppCompatActivity {

    TextInputEditText oldpass,newpass,enterpass;

    Button resetpass;

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        initView();
        initEvent();
    }

    private void initEvent() {
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sOldpass = oldpass.getText().toString();
                String sNewpass = newpass.getText().toString();
                String sEnterpass = enterpass.getText().toString();
                if (!(TextUtils.isEmpty(sOldpass) || TextUtils.isEmpty(sNewpass) || TextUtils.isEmpty(sEnterpass))){
                    if (!(sNewpass.equals(sOldpass))){
                        if (sEnterpass.equals(sNewpass)){
                            updatePassword(sOldpass,sNewpass);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetActivity.this);
                            builder.setTitle("无法修改").setMessage("新密码与确认密码不一致")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ResetActivity.this);
                        builder.setTitle("无法修改").setMessage("新密码不能与旧密码一致")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetActivity.this);
                    builder.setTitle("无法修改").setMessage("修改密码不能为空")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    public void updatePassword(String oldPass,String newPass){
        HashMap<String, String> map = new HashMap<>();
        map.put("newPassword", newPass);
        map.put("oldPassword", oldPass);
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json;charset=UTF-8"));
        final Request request = new Request.Builder()
                .url(Constant.IP + Constant.RESET_PWD)
                .put(requestBody)
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
                                PojoChange pojoChange = new Gson().fromJson(strJson, PojoChange.class);
                                if (pojoChange.getCode() == 200) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetActivity.this);
                                            builder.setTitle("修改成功")
                                                    .setMessage("密码修改成功，请重新登录")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            startActivity(new Intent(ResetActivity.this, LoginActivity.class));
                                                            Constant.setOffLine();
                                                            finish();
                                                        }
                                                    });
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetActivity.this);
                                            builder.setTitle("修改失败")
                                                    .setMessage(pojoChange.getMsg())
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                        }
                                                    });
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
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

    private void initView() {

        oldpass = findViewById(R.id.reset_oldpass);
        newpass = findViewById(R.id.reset_newpass);
        enterpass = findViewById(R.id.reset_enterpass);
        resetpass = findViewById(R.id.reset_bt);
    }
}