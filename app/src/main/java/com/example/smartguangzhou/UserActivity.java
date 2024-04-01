package com.example.smartguangzhou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoResult;
import com.example.smartguangzhou.pojo.PojoUser;
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

public class UserActivity extends AppCompatActivity {
    private ImageView imageUser;
    private EditText editUsername;
    private RadioButton rbSex0;
    private RadioButton rbSex1;
    private EditText editPhone;
    private Button btnOk;

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();
        initEvent();
    }

    private void initView() {
        imageUser = (ImageView) findViewById(R.id.image_user);
        editUsername = (EditText) findViewById(R.id.edit_username);
        rbSex0 = (RadioButton) findViewById(R.id.rb_sex_0);
        rbSex1 = (RadioButton) findViewById(R.id.rb_sex_1);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        btnOk = (Button) findViewById(R.id.btn_Ok);
        getUserRequest();
    }

    private void initEvent() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editUsername.getText().toString().trim();
                String phoneNumber = editPhone.getText().toString().trim();
                String sex = rbSex0.isChecked() ? "0" : "1";
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(UserActivity.this,"昵称不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneNumber.length() != 11) {
                    Toast.makeText(UserActivity.this,"联系电话错误！",Toast.LENGTH_SHORT).show();
                    return;
                }
                putUserInfoRequest(name,phoneNumber,sex);
            }
        });
    }

    //修改用户基本信息
    public void putUserInfoRequest(String name,String phone,String sex){
        String url = Constant.IP + Constant.UPDATE_USER_URL;

        HashMap<String, String> map = new HashMap<>();
        map.put("nickName", name);
        map.put("phonenumber", phone);
        map.put("sex", sex);
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(json,MediaType.parse("application/json;charset=UTF-8"));

        Request build = new Request.Builder()
                .url(url)
                .put(requestBody)
                .header("Authorization",Constant.TOKEN)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                client.newCall(build).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws
                            IOException {
                        String strJson = response.body().string();
                        if (response.isSuccessful()) {
                            PojoResult pojoOkResult = new
                                    Gson().fromJson(strJson, PojoResult.class);
                            if (pojoOkResult.getCode() == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(UserActivity.this, "个人信息修改成功！", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(UserActivity.this,pojoOkResult.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }).start();
    }

    //获取用户基本信息
    public void getUserRequest(){
        final Request request = new Request.Builder()
                .url(Constant.IP + Constant.GET_INFO_URL)
                .header("Authorization",Constant.TOKEN)
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
                        public void onResponse(Call call, Response response)
                                throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoUser pojoUser = new Gson().fromJson(strJson,
                                        PojoUser.class);
                                if (pojoUser.getCode() == 200) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String avatar =
                                                    pojoUser.getUser().getAvatar();
                                            if (TextUtils.isEmpty(avatar)) {
                                                imageUser.setImageResource(R.drawable.person);
                                            } else {
                                                Glide.with(UserActivity.this)
                                                        .load(Constant.IP + "/prod-api" +avatar)
                                                        .into(imageUser);
                                            }
                                            editUsername.setText(pojoUser.getUser().getNickName());
                                            String phone =
                                                    pojoUser.getUser().getPhonenumber().toString();
                                            if (phone.length() == 11) {
                                                editPhone.setText(phone.substring(0, 4) + "****" + phone.substring(8, 11));
                                            }
                                            rbSex0.setChecked("0".equals(pojoUser.getUser().getSex()));
                                            rbSex1.setChecked("1".equals(pojoUser.getUser().getSex()));
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UserActivity.this,"请先登录", Toast.LENGTH_SHORT).show();
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