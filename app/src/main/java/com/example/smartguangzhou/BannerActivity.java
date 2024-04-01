package com.example.smartguangzhou;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.smartguangzhou.Utils.Constant;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    Banner banner;
    Button join,ipset;

    List<Integer> imageResId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initView();
        initEvent();
    }

    private void initEvent() {
        banner.setAdapter(new BannerImageAdapter<Integer>(imageResId) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        holder.imageView.setImageResource(data);
                    }
                })
                .addBannerLifecycleObserver(this)//添加生命周期
                .setIndicator(new CircleIndicator(this))//设置指示器样式 圆形，
                .start();

        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position ==4){
                    banner.stop();//停止轮播图
                    join.setVisibility(View.VISIBLE);
                    ipset.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BannerActivity.this,MainActivity.class));
                finish();
            }
        });
        ipset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(BannerActivity.this);
                dialog.setContentView(R.layout.pop_ipset);
                dialog.setCancelable(true);

                EditText pop_setip = dialog.findViewById(R.id.pop_setip);
                CheckBox pop_remip = dialog.findViewById(R.id.pop_remip);
                pop_setip.setText("124.93.196.45:10001");
                Button pop_confirm = dialog.findViewById(R.id.pop_confirm);

                pop_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ip = pop_remip.getText().toString();
                        Constant.IP = ip;
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void initView() {
        banner = findViewById(R.id.banner_banner);
        join = findViewById(R.id.banner_join);
        ipset = findViewById(R.id.banner_setip);
        imageResId.add(R.drawable.banner1);
        imageResId.add(R.drawable.banner2);
        imageResId.add(R.drawable.banner3);
        imageResId.add(R.drawable.banner4);
        imageResId.add(R.drawable.banner5);
    }

}