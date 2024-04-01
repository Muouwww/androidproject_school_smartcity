package com.example.smartguangzhou;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoLogin;
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

public class LoginActivity extends AppCompatActivity {

    TextInputEditText user,pass;

    TextView toReg,toReset;
    Button login,cancel;

    String ROGToken;
    OkHttpClient client = new OkHttpClient();

    CheckBox remPass,autoLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();
        initEvent();
    }

    private void initEvent() {
        toReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetActivity.class));
            }
        });
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user.getText().toString();
                String key = pass.getText().toString();
                if (remPass.isChecked()){
                    editor.putBoolean("remember_me", true);
                    editor.putString("username", name);
                    editor.putString("password", key);
                    editor.apply();
                }else{
                    editor.clear();
                }
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("登录失败！")
                            .setMessage("账号密码不能为空！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    postLoginRequest(name,key);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void postLoginRequest(String user, String key) {
        String url = Constant.IP;
        HashMap<String, String> map = new HashMap<>();
        map.put("username", user);
        map.put("password", key);
        String json = new Gson().toJson(map);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json;charset=UTF-8"));
        final Request request = new Request.Builder()
                .url(url+Constant.LOGIN)
                .post(body)
                .build();
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
                        public void onResponse(Call call, Response response) throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoLogin pojoLogin = new Gson().fromJson(strJson, PojoLogin.class);
                                if (pojoLogin.getCode() == 200) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Constant.ISLOGIN = true;
                                            Constant.TOKEN = pojoLogin.getToken();
                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, pojoLogin.getMsg(),Toast.LENGTH_SHORT).show();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setTitle("登录失败！")
                                                    .setMessage(pojoLogin.getMsg())
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
        remPass = findViewById(R.id.rem_pass);
        autoLogin = findViewById(R.id.auto_pass);
        user = findViewById(R.id.login_user);
        pass = findViewById(R.id.login_pass);
        toReg = findViewById(R.id.login_to_reg);
        toReset = findViewById(R.id.login_to_reset);
        login = findViewById(R.id.login_login);
        cancel = findViewById(R.id.login_cancel);

        sharedPreferences = getSharedPreferences("App",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("remember_me",false)){
            user.setText(sharedPreferences.getString("username",""));
            pass.setText(sharedPreferences.getString("password",""));
            remPass.setChecked(true);
        }
    }
}