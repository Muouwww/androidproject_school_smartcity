package com.example.smartguangzhou.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartguangzhou.FeedActivity;
import com.example.smartguangzhou.LoginActivity;
import com.example.smartguangzhou.MainActivity;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.ResetActivity;
import com.example.smartguangzhou.UserActivity;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoUser;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class personal extends Fragment {

    ImageView icon;
    TextView tvPersonal,tvOrder,tvChange,tvFeed,iconText;

    Button person_bt;

    OkHttpClient client = new OkHttpClient();

    public personal() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();

    }

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
                        }
                        @Override
                        public void onResponse(Call call, Response response)
                                throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoUser pojoUser = new Gson().fromJson(strJson,
                                        PojoUser.class);
                                if (pojoUser.getCode() == 200) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String avatar = pojoUser.getUser().getAvatar();
                                            if (TextUtils.isEmpty(avatar)) {
                                                icon.setImageResource(R.drawable.personicon);
                                            } else {
                                                Glide.with(getActivity().getApplicationContext())
                                                        .load(Constant.IP +"/prod-api" + avatar)
                                                        .into(icon);
                                            }
                                            iconText.setText(pojoUser.getUser().getNickName());
                                        }
                                    });
                                }else{
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//                                            GuideActivity.sp.edit().putString(Const.TOKEN, "").apply();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setTitle("未登录")
                                                    .setMessage("检测到未登录，是否前往登录？")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            startActivity(new Intent(getActivity(), LoginActivity.class));
                                                        }
                                                    })
                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
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

    private void initEvent() {
        person_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (person_bt.getText().toString().equals("退出")){
                    Constant.ISLOGIN = false;
                    Toast.makeText(getActivity(), "账号已登出", Toast.LENGTH_SHORT).show();
                    onResume();
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.ISLOGIN){
                    startActivity(new Intent(getActivity(), ResetActivity.class));
                }else{
                    Toast.makeText(getActivity(), "检测到您未登录，请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserActivity.class));
            }
        });
        tvFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FeedActivity.class));
            }
        });
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "功能预备上线...", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initView() {


        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        TextView toolbarText = getView().findViewById(R.id.toolbar_title);
        toolbarText.setText("用户中心");

        iconText = getView().findViewById(R.id.tv_username);
        icon = getView().findViewById(R.id.img_usericon);
        tvPersonal = getView().findViewById(R.id.tv_personalinfo);
        tvOrder = getView().findViewById(R.id.tv_orderlist);
        tvChange = getView().findViewById(R.id.tv_changepass);
        tvFeed = getView().findViewById(R.id.tv_feedback);
        person_bt = getView().findViewById(R.id.exit);
        ConstraintLayout personalbg = getView().findViewById(R.id.personalbg);
        if (Constant.ISLOGIN){
            person_bt.setText("退出");
            personalbg.setBackgroundResource(R.drawable.gr_bg2);
        }else{
            person_bt.setText("登录账号");
            personalbg.setBackgroundResource(R.drawable.gr_bg);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserRequest();
        initView();

    }
}