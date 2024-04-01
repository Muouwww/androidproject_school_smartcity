package com.example.smartguangzhou.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartguangzhou.Adapter.RvHotNewsAdapter;
import com.example.smartguangzhou.Adapter.RvTypeNewsAdapter;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoNews;
import com.example.smartguangzhou.pojo.PojoNewsCategory;
import com.example.smartguangzhou.pojo.PojoNewsDetail;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class news extends Fragment {
    private RecyclerView rvHotNews;
    private TabLayout tabNewsType;
    private RecyclerView rvNews;

    OkHttpClient client = new OkHttpClient();
    public List<PojoNews.RowsNewsPojo> rowsHotNewsPojo_list = new ArrayList<>();
    public List<PojoNewsDetail.DataPojo> rowsNewsDetailPojos_list = new ArrayList<>();
    public List<PojoNewsCategory.Datapojo> rowsNewsCategoryPojos_list = new ArrayList<>();
    public List<PojoNews.RowsNewsPojo> rowsTypeNewsPojos_list = new ArrayList<>();
    public news() {
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
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initEvent();

        getHotNewsData();
        getNewsCategoryData();
    }

    private void initView(){
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        TextView toolbarText = getView().findViewById(R.id.toolbar_title);
        toolbarText.setText("新闻资讯");
        rvHotNews = getView().findViewById(R.id.rv_hot_news);
        tabNewsType = getView().findViewById(R.id.tab_news_type);
        rvNews = getView().findViewById(R.id.rv_news);

    }

    private void  initEvent(){
        tabNewsType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getNewsData(((int) tab.getTag()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getHotNewsData() {
        String url = Constant.IP+ Constant.NEWS_URL;
        String params = "?Hot=Y";
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
                                PojoNews pojoNews = new Gson().fromJson(strJson, PojoNews.class);
                                rowsHotNewsPojo_list = pojoNews.getRows();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRVHotNews();
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

    private void initRVHotNews() {
        RvHotNewsAdapter rvHotNewsAdapter = new RvHotNewsAdapter(getActivity(), null);
        rvHotNews.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvHotNews.setAdapter(rvHotNewsAdapter);
        rvHotNewsAdapter.setData(rowsHotNewsPojo_list);
    }

    private void getNewsCategoryData(){
        String url = Constant.IP+Constant.CATEGORY_URL;
        Request request = new Request.Builder().url(url).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        } @
                                Override
                        public void onResponse(Call call, Response response)
                                throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoNewsCategory pojoNewsCategory = new Gson().fromJson(strJson, PojoNewsCategory.class);
                                rowsNewsCategoryPojos_list = pojoNewsCategory.getData();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initTabNews();
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

    private void initTabNews(){
        if(rowsNewsCategoryPojos_list == null)
            return;
        for(int i=0;i<rowsNewsCategoryPojos_list.size();i++){
            tabNewsType.addTab(tabNewsType.newTab()
                    .setText(rowsNewsCategoryPojos_list.get(i).getName())
                    .setTag(rowsNewsCategoryPojos_list.get(i).getId()));
        }
    }

    private void getNewsData(int type) {
        String url = Constant.IP+Constant.NEWS_URL;
        String params = "?type="+type;
        Request request = new Request.Builder().url(url+params).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        } @
                                Override
                        public void onResponse(Call call, Response response)
                                throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoNews pojoNews = new Gson().fromJson(strJson,
                                        PojoNews.class);
                                rowsTypeNewsPojos_list = pojoNews.getRows();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initRVNews();
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

    private void initRVNews(){
        RvTypeNewsAdapter rvTypeNewsAdapter = new
                RvTypeNewsAdapter(getActivity(), null);
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        rvNews.addItemDecoration(new DividerItemDecoration(getActivity(),RecyclerView.VERTICAL));
        rvNews.setAdapter(rvTypeNewsAdapter);
        rvTypeNewsAdapter.setData(rowsTypeNewsPojos_list);
    }
}