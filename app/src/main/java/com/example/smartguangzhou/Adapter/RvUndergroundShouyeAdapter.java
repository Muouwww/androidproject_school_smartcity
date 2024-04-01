package com.example.smartguangzhou.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartguangzhou.MetroActivity;
import com.example.smartguangzhou.R;
import com.example.smartguangzhou.pojo.PojoUndergroundTop;

import java.util.ArrayList;
import java.util.List;

public class RvUndergroundShouyeAdapter extends RecyclerView.Adapter<RvUndergroundShouyeAdapter.ViewHolder> {
    public Context context;
    public List<PojoUndergroundTop.Data> data = new ArrayList<>();

    public RvUndergroundShouyeAdapter(Context context, List<PojoUndergroundTop.Data> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<PojoUndergroundTop.Data> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvUndergroundShouyeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_underground_shouye,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvUndergroundShouyeAdapter.ViewHolder holder, int position) {
        holder.showItem(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView metroNumber,nextStation,nextTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            metroNumber = itemView.findViewById(R.id.shouye_stationname);
            nextStation = itemView.findViewById(R.id.shouye_nextstation);
            nextTime = itemView.findViewById(R.id.shouye_nextstation_time);


        }

        @SuppressLint("SetTextI18n")
        public void showItem(PojoUndergroundTop.Data data) {
            metroNumber.setText(data.getLineName());
            nextStation.setText("下一站："+data.getNextStep().getName());
            nextTime.setText(data.getReachTime()+"分钟");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(context,MetroActivity.class);
                    it.putExtra("lineId",data.getLineId());
                    context.startActivity(it);
                }
            });
        }
    }
}
