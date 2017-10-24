package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.entity.ZiFenlei;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mo on 2017/10/9.
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridViewHolder>{

    private Context context;
    private List<ShouyeFenlei.DataBean> list;


    public GridViewAdapter(Context context, List<ShouyeFenlei.DataBean> list) {
        this.context = context;
            this.list=list;
    }



    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.grid_item,null);
        GridViewHolder holder = new GridViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        holder.tv.setText(list.get(position).name);
        Glide.with(context).load(list.get(position).icon).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class GridViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv;
        public GridViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_grid);
            tv=itemView.findViewById(R.id.tv_grid);
        }
    }
}

