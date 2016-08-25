package cn.ac.ict.yxd.itug.detection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ac.ict.yxd.itug.R;

/**
 * Created by Shaobo on 8/23/16.
 */

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {
    private ArrayList<SensorItem> listData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
            sensorLabelView = (TextView) v.findViewById(R.id.tv_sensorLabel);
            sensorNameView = (TextView) v.findViewById(R.id.tv_sensorName);
        }
        TextView sensorLabelView;
        TextView sensorNameView;
    }

    public SensorAdapter(ArrayList<SensorItem> listData) {
        this.listData = listData;
    }

    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_sensor, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.sensorNameView.setText(listData.get(position).getSensorName());
        holder.sensorLabelView.setText(listData.get(position).getSensorLabel());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}

