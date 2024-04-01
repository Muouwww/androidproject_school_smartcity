package com.example.smartguangzhou.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartguangzhou.R;
import com.example.smartguangzhou.pojo.PojoMetroLine;

import java.util.ArrayList;
import java.util.List;

public class RvMetroLineAdapter extends RecyclerView.Adapter<RvMetroLineAdapter.ViewHolder> {
    public Context context;
    public List<PojoMetroLine.Data.MetroStepList> stepData = new ArrayList<>();
    public String runStation;
    public RvMetroLineAdapter(Context context, List<PojoMetroLine.Data.MetroStepList> stepData) {
        this.context = context;
        setData(stepData,runStation);
    }


    public void setData(List<PojoMetroLine.Data.MetroStepList> stepData,String runStation) {
        this.stepData.clear();
        if (stepData != null) {
            this.stepData.addAll(stepData);
        }
        if (!TextUtils.isEmpty(runStation)){
            this.runStation = runStation;
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RvMetroLineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_metroline,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvMetroLineAdapter.ViewHolder holder, int position) {

        PojoMetroLine.Data.MetroStepList stepList = stepData.get(position);
        holder.showItem(stepList);

    }

    @Override
    public int getItemCount() {
        return stepData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView metroicon;
        TextView metrolineinfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            metroicon = itemView.findViewById(R.id.metrocaricon);
            metroicon.setVisibility(View.INVISIBLE);
            metrolineinfo = itemView.findViewById(R.id.metroline_info);
        }
        @SuppressLint("SetTextI18n")
        public void showItem(PojoMetroLine.Data.MetroStepList stepList) {
            metrolineinfo.setText("●"+stepList.getName());
            boolean isRunningTostation = runStation.equals(stepList.getName());
            showMetroIcon(isRunningTostation);

        }
        public void showMetroIcon(boolean show){
            if (show) {
                metroicon.setVisibility(View.VISIBLE);
            } else {
                metroicon.setVisibility(View.INVISIBLE);
            }
        }
    }
}
