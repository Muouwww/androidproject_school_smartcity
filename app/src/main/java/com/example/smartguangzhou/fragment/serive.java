package com.example.smartguangzhou.fragment;

import android.app.Service;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.smartguangzhou.Adapter.ServiceAdapter;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class serive extends Fragment {

    RecyclerView service_item;

    RadioGroup serviceTypeRadioGroup;

    OkHttpClient client;
    private List<PojoService.Rows> serviceList = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();
        getTypeServiceData("车主服务");
    }

    private void initEvent() {
        serviceTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = getView().findViewById(i);
                String Type = radioButton.getText().toString();
                getTypeServiceData(Type);
            }
        });

    }

    private void initView() {

        client = new OkHttpClient();
        service_item = getView().findViewById(R.id.service_item);
        serviceTypeRadioGroup = getView().findViewById(R.id.rg_type);
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        TextView toolbarText = getView().findViewById(R.id.toolbar_title);
        toolbarText.setText("全部服务");
    }

    public void getTypeServiceData(String sType) {
        String url = Constant.IP + Constant.ALL_SERVICE;
        String params = "?serviceType="+sType;
        Request request = new Request.Builder().url(url + params).build();
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
                                PojoService pojoService = new Gson().fromJson(strJson, PojoService.class);
                                serviceList = pojoService.getRows();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRVService();
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

    //服务更新UI
    private void initRVService() {
        Collections.sort(serviceList, new Comparator<PojoService.Rows>() {
            @Override
            public int compare(PojoService.Rows r1, PojoService.Rows r2) {
                return r2.getId()-r1.getId();
            }
        });

        ServiceAdapter serviceAdapter = new ServiceAdapter(serviceList, getActivity());
        service_item.setLayoutManager(new GridLayoutManager(getActivity(),4));
        service_item.setAdapter(serviceAdapter);
        serviceAdapter.setServiceList(serviceList);
    }

    public serive() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_serive, container, false);
    }
}