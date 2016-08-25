package cn.ac.ict.yxd.itug.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ac.ict.yxd.itug.R;
import android.app.Activity;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Shaobo on 8/23/16.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ArrayList<MenuItem> listData;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
            menuIconView = (ImageView) v.findViewById(R.id.ic_menu_item);
            menuHeaderView = (TextView) v.findViewById(R.id.tv_menuHeader);
            menuDescriptionView = (TextView) v.findViewById(R.id.tv_menuDescription);
            menuExecutionView = (TextView) v.findViewById(R.id.tv_menuExecution);
        };

        ImageView menuIconView;
        TextView menuHeaderView;
        TextView menuDescriptionView;
        TextView menuExecutionView;

    }

    public MenuAdapter(ArrayList<MenuItem> listData) {
        this.listData = listData;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.menuIconView.setImageResource(listData.get(position).iconDrawableId);
        holder.menuIconView.setColorFilter(Color.parseColor("#757575"));
        holder.menuHeaderView.setText(listData.get(position).headerString);
        holder.menuDescriptionView.setText(listData.get(position).descriptionString);
        holder.menuExecutionView.setText(listData.get(position).executionString);

        final Intent intent = listData.get(position).menuIntent;

        if (intent != null) {
            holder.menuExecutionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(intent);
                }
            });
        } else {
            holder.menuExecutionView.setText("未完成");
            holder.menuExecutionView.setTextColor(Color.parseColor("#f44336"));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}

