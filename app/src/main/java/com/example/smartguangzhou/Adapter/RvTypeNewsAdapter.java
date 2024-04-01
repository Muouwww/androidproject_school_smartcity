package com.example.smartguangzhou.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.example.smartguangzhou.TestActivity;
import com.example.smartguangzhou.Utils.Constant;
import com.example.smartguangzhou.pojo.PojoNews;

import java.util.List;

public class RvTypeNewsAdapter extends RecyclerView.Adapter<RvTypeNewsAdapter.ViewHolder> {
    public Context context;
    public List<PojoNews.RowsNewsPojo> data;


    public RvTypeNewsAdapter(Context context, List<PojoNews.RowsNewsPojo> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<PojoNews.RowsNewsPojo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showItem(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageTypenewsIcon;
        private TextView tvTypenewsName;
        private TextView tvTypenewsContent;
        private TextView tvTypenewsTime;
        private TextView tvTypenewsComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTypenewsIcon = (ImageView)
                    itemView.findViewById(R.id.iv_news_icon);
            tvTypenewsName = (TextView)
                    itemView.findViewById(R.id.tv_news_name);
            tvTypenewsContent = (TextView)
                    itemView.findViewById(R.id.tv_news_content);
            tvTypenewsTime = (TextView)
                    itemView.findViewById(R.id.tv_news_time);
            tvTypenewsComment = (TextView)
                    itemView.findViewById(R.id.tv_typenews_comment);
        }
        @SuppressLint("SetTextI18n")
        public void showItem(PojoNews.RowsNewsPojo rowsNewsPojo){
            Glide.with(context)
                    .load(Constant.IP+rowsNewsPojo.getCover())
                    .into(imageTypenewsIcon);
            tvTypenewsName.setText(rowsNewsPojo.getTitle());
            tvTypenewsContent.setText(Html.fromHtml(rowsNewsPojo.getContent()));
            if(rowsNewsPojo.getCommentNum()!=0){
                tvTypenewsComment.setText("💬" + rowsNewsPojo.getCommentNum());
            }else {
                tvTypenewsComment.setText("💬 "+0);
            }
            tvTypenewsTime.setText(rowsNewsPojo.getCreateTime());
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
