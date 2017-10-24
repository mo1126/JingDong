package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mo.jingdong.R;
import com.mo.jingdong.XiangqingActivity;
import com.mo.jingdong.XiangqingListActivity;
import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.entity.ZiFenlei;

import java.util.List;

/**
 * Created by Mo on 2017/10/9.
 */

public class FenleiRightItemAdapter extends RecyclerView.Adapter<FenleiRightItemAdapter.GridViewHolder>{

    private Context context;
    private List<ZiFenlei.DataBean.ListBean> list;

    public FenleiRightItemAdapter(Context context, List<ZiFenlei.DataBean.ListBean>   list) {
        this.context = context;
        this.list=list;
    }


    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fenlei_right_grid_item,null);
        GridViewHolder holder = new GridViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
        holder.tv.setText(list.get(position).name);
        Glide.with(context).load(list.get(position).icon).into(holder.iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, XiangqingListActivity.class);
                intent.putExtra("pcid",list.get(position).pscid+"");
                context.startActivity(intent);
                System.out.println(list.get(position).pscid+"============================================");
            }
        });
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
            iv=itemView.findViewById(R.id.iv_grid_fenlei);
            tv=itemView.findViewById(R.id.tv_grid_fenlei);
        }
    }


}

