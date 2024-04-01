package com.example.smartguangzhou.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartguangzhou.Adapter.RvHotNewsAdapter;
import com.example.smartguangzhou.Adapter.RvServiceAdapter;
import com.example.smartguangzhou.Adapter.RvTypeNewsAdapter;
import com.example.smartguangzhou.MainActivity;
import com.example.smartguangzhou.NewsSearchActivity;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoBanner;
import com.example.smartguangzhou.pojo.PojoNews;
import com.example.smartguangzhou.pojo.PojoNewsCategory;
import com.example.smartguangzhou.pojo.PojoService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

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

public class home extends Fragment {
    Banner banner;
    OkHttpClient client;

    SearchView searchView;

    TabLayout tabNewsType;

    public List<PojoService.Rows> rowsServicePojos_list = new ArrayList<>();
    public List<PojoNews.RowsNewsPojo> rowsHotNewsPojos_list = new ArrayList<>();

    public List<PojoNews.RowsNewsPojo> rowsTypeNewsPojos_list = new ArrayList<>();
    public List<PojoNewsCategory.Datapojo> rowsNewsCategoryPojos_list = new ArrayList<>();
    RecyclerView rvService, rvHotNews, rvNews;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();
        getServiceData();
        getHotNewsData();
        getNewsCategoryData();
    }

    private void getNewsData(int type) {
        String url = Constant.IP + Constant.NEWS_URL;
        String params = "?type=" + type;
        Request request = new Request.Builder().url(url + params).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response)throws IOException {
                            String strJson = response.body().string();
                            if (response.isSuccessful()) {
                                PojoNews pojoNews = new Gson().fromJson(strJson,PojoNews.class);
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


    public void initRVNews() {
        RvTypeNewsAdapter rvTypeNewsAdapter = new RvTypeNewsAdapter(getActivity(), null);
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvNews.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));
        rvNews.setAdapter(rvTypeNewsAdapter);
        rvTypeNewsAdapter.setData(rowsTypeNewsPojos_list);
    }

    private void getNewsCategoryData() {
        String url = Constant.IP + Constant.CATEGORY_URL;
        Request request = new Request.Builder().url(url).build();
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

    private void initTabNews() {
        if (rowsNewsCategoryPojos_list == null)
            return;
        for (int i = 0; i < rowsNewsCategoryPojos_list.size(); i++) {
            tabNewsType.addTab(tabNewsType.newTab()
                    .setText(rowsNewsCategoryPojos_list.get(i).getName())
                    .setTag(rowsNewsCategoryPojos_list.get(i).getId()));
        }
    }

    private void initEvent() {
        Request request = new Request.Builder()
                .url(Constant.IP + Constant.HOMEBANNER)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    PojoBanner pojoBanner = new Gson().fromJson(json, PojoBanner.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<PojoBanner.RowsDTO> bannerItems = pojoBanner.getRows();
                            banner.setAdapter(new BannerImageAdapter<PojoBanner.RowsDTO>(bannerItems) {
                                @Override
                                public void onBindView(BannerImageHolder holder, PojoBanner.RowsDTO data, int position, int size) {
                                    Glide.with(holder.itemView)
                                            .load(Constant.IP + data.getAdvImg())
                                            .into(holder.imageView);
                                }
                            }).start();
                        }
                    });
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(getActivity(), NewsSearchActivity.class).putExtra("query", query));
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getServiceData();
        tabNewsType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getNewsData((int)tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void getServiceData() {
        String url = Constant.IP + Constant.ALL_SERVICE;
        String params = "?pageNum=1&pageSize=9";
        Request request = new Request.Builder().url(url + params).build();
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
                            if (response.isSuccessful()) {
                                PojoService pojoService = new Gson().fromJson(strJson, PojoService.class);
                                rowsServicePojos_list = pojoService.getRows();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            initRVService();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
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

    private void initView() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE);

        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        TextView toolbarText = getView().findViewById(R.id.toolbar_title);
        toolbarText.setText("智慧广州");
        searchView = getView().findViewById(R.id.search_view);
        rvService = getView().findViewById(R.id.rv_service);
        banner = getView().findViewById(R.id.home_banner);
        client = new OkHttpClient();
        rvHotNews = getView().findViewById(R.id.rv_hot_news);
        tabNewsType = getView().findViewById(R.id.tab_news_type);
        rvNews = (RecyclerView) getView().findViewById(R.id.rv_news);
    }

    private void initRVService() {
        Collections.sort(rowsServicePojos_list, new Comparator<PojoService.Rows>() {
            @Override
            public int compare(PojoService.Rows o1, PojoService.Rows o2) {
                return o2.getId() - o1.getId();
            }
        });
        PojoService.Rows rowsMore = new PojoService.Rows();
        rowsMore.setServiceName("更多服务");
        rowsServicePojos_list.add(rowsMore);
        RvServiceAdapter rvServiceAdapter = new RvServiceAdapter(getActivity(), null);
        rvService.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        rvService.setAdapter(rvServiceAdapter);
        rvServiceAdapter.setData(rowsServicePojos_list);
    }

    //热点新闻获取数据
    private void getHotNewsData() {
        String url = Constant.IP + Constant.NEWS_URL;
        String params = "?hot=Y";
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
                                rowsHotNewsPojos_list = pojoNews.getRows();
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
        rvHotNewsAdapter.setData(rowsHotNewsPojos_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}