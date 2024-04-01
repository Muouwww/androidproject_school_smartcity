package com.example.smartguangzhou.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartguangzhou.Adapter.RvUndergroundShouyeAdapter;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoUndergroundTop;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class underground extends Fragment {
    RecyclerView rvMetro;
    TextView metroInfo;
    SearchView searchView;

    OkHttpClient client = new OkHttpClient();
    public List<PojoUndergroundTop.Data> pojoMetro = new ArrayList<>();

    public underground() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();
        initSearch();
    }

    private void initSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                showUnderground(s);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public void showUnderground(String params){
        metroInfo.setText(params);
        String url = Constant.IP + Constant.METROE_INFO_DATA;
        String Params = "?currentName="+params;
        Request request = new Request.Builder()
                .url(url + Params)
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
                                PojoUndergroundTop pojoUndergroundTop = new Gson().fromJson(strJson, PojoUndergroundTop.class);
                                if (!pojoUndergroundTop.getData().isEmpty()){
                                    pojoMetro = pojoUndergroundTop.getData();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            initMetro();
                                        }
                                    });
                                }else{
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "输入错误！请检查站点是否有误", Toast.LENGTH_SHORT).show();
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
    private void initEvent() {
        String url = Constant.IP + Constant.METROE_INFO_DATA;
        String params = "?currentName=建国门";
        Request request = new Request.Builder()
                .url(url + params)
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
                                PojoUndergroundTop pojoUndergroundTop = new Gson().fromJson(strJson, PojoUndergroundTop.class);
                                if (!pojoUndergroundTop.getData().isEmpty()){
                                    pojoMetro = pojoUndergroundTop.getData();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            initMetro();
                                        }
                                    });
                                }else{
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "输入错误！请检查站点是否有误", Toast.LENGTH_SHORT).show();
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

    public void initMetro(){
        RvUndergroundShouyeAdapter rvUndergroundShouyeAdapter = new RvUndergroundShouyeAdapter(getActivity(),null);
        rvMetro.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMetro.setAdapter(rvUndergroundShouyeAdapter);
        rvUndergroundShouyeAdapter.setData(pojoMetro);
    }
    private void initView() {
        rvMetro = getView().findViewById(R.id.stationShouye_underground);
        metroInfo = getView().findViewById(R.id.metro_info);
        searchView = getView().findViewById(R.id.search_undergrounds);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_underground, container, false);
    }
}