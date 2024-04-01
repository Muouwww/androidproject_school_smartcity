package com.example.smartguangzhou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartguangzhou.NewsDetailActivity;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoNews;

import java.util.List;

public class RvHotNewsAdapter  extends  RecyclerView.Adapter<RvHotNewsAdapter.ViewHolder>{
    public Context context;
    public List<PojoNews.RowsNewsPojo> data;

    public RvHotNewsAdapter(Context context, List<PojoNews.RowsNewsPojo> data) {
        this.context = context;
        this.data = data;
    }
    public void setData(List<PojoNews.RowsNewsPojo> data){
        this.data = data;
        notifyDataSetChanged();
    } @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_news, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showItem(data.get(position));
    }
    @Override
    public int getItemCount() {
        if(data != null)
            return data.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageHotnewsIcon;
        private TextView tvHotnewsName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHotnewsIcon = (ImageView)itemView.findViewById(R.id.image_hotnews_icon);
            tvHotnewsName = (TextView)itemView.findViewById(R.id.tv_hotnews_name);

        }

        public void showItem(PojoNews.RowsNewsPojo rowsNewsPojo){
            Glide.with(context).load(Constant.IP + rowsNewsPojo.getCover()).into(imageHotnewsIcon);
            tvHotnewsName.setText(rowsNewsPojo.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(context, NewsDetailActivity.class);
                    it.putExtra("id",rowsNewsPojo.getId());
                    context.startActivity(it);
                }
            });
        }
    }
}
