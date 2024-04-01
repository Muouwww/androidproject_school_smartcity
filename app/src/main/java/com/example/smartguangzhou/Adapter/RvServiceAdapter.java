package com.example.smartguangzhou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartguangzhou.MainActivity;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.TestActivity;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoService;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class RvServiceAdapter extends RecyclerView.Adapter<RvServiceAdapter.ViewHolder> {
    public Context context;
    public List<PojoService.Rows> data;

    public RvServiceAdapter(Context context,List<PojoService.Rows> data){
        this.context = context;
        this.data = data;
    }

    public void setData(List<PojoService.Rows> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_service,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvServiceAdapter.ViewHolder holder, int position) {
        holder.showItem(data.get(position));

    }

    @Override
    public int getItemCount() {
        if (data!=null)
            return data.size();
        else
            return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageServiceIcon;
        private TextView tvServiceName;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageServiceIcon = (ImageView)itemView.findViewById(R.id.image_service_icon);
            tvServiceName = (TextView)itemView.findViewById(R.id.tv_service_name);
        }

        public void showItem(PojoService.Rows rowsServicePojo){
            String name = rowsServicePojo.getServiceName();
            if(name.equals("更多服务")){
                imageServiceIcon.setImageResource(R.drawable.ic_more);
            }else {
                Glide.with(context)
                        .load(Constant.IP + rowsServicePojo.getImgUrl())
                        .into(imageServiceIcon);
            }
            tvServiceName.setText(rowsServicePojo.getServiceName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(name.equals("更多服务")){
                        ((MainActivity)context).updagePage(R.id.nav_item2);
                    }else if(name.equals("城市地铁")){
                        ((MainActivity)context).updagePage(R.id.nav_item3);
                    }else {
                        context.startActivity(new Intent(context, TestActivity.class).putExtra("name",rowsServicePojo.getServiceName()));
                    }
                }
            });
        }
    }
}
