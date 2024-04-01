package com.example.smartguangzhou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartguangzhou.MainActivity;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoService;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    public List<PojoService.Rows> serviceList;
    public Context context;

    public ServiceAdapter(List<PojoService.Rows> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
    }

    public void setServiceList(List<PojoService.Rows> serviceList) {
        this.serviceList = serviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_service, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showItem(serviceList.get(position));
    }


    @Override
    public int getItemCount() {
        if(serviceList != null)
            return serviceList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView serviceIconImageView;
        private TextView serviceNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceIconImageView = itemView.findViewById(R.id.image_service_icon);
            serviceNameTextView = itemView.findViewById(R.id.tv_service_name);
        }

        public void showItem(PojoService.Rows rowsServicePojo) {
            String name = rowsServicePojo.getServiceName();
            Log.d("ServiceAdapter", "Service name: " + name);
            if (name.equals("更多服务")) {
                Log.d("ServiceAdapter", "Setting more service icon");
                serviceIconImageView.setImageResource(R.drawable.ic_more);
            } else {
                String imgUrl = Constant.IP + rowsServicePojo.getImgUrl();
                Log.d("ServiceAdapter", "Image URL: " + imgUrl);
                Glide
                        .with(context)
                        .load(imgUrl)
                        .into(serviceIconImageView);
            }
            serviceNameTextView.setText(rowsServicePojo.getServiceName());
            serviceIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (serviceNameTextView.getText().toString().equals("城市地铁")){
                        ((MainActivity)context).updagePage(R.id.nav_item3);
                    }else {
                        Toast.makeText(context, "服务暂未开通", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            if (name.equals("城市地铁")){
//                ((MainActivity)context).updagePage(R.id.nav_item3);
//            }
        }
    }
}