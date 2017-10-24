package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mo.jingdong.R;
import com.mo.jingdong.XiangqingActivity;
import com.mo.jingdong.entity.Xbanners;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Mo on 2017/10/7.
 */

public class RecyclerAdapter extends CommonAdapter<Xbanners.TuijianBean.ListBean> {
    public Context context;
    public List<Xbanners.TuijianBean.ListBean> list;
    public int mlayoutId;
    private View inflate;


    public RecyclerAdapter(Context context, int layoutId, List<Xbanners.TuijianBean.ListBean> list) {
        super(context, layoutId,list);
        this.context=context;
        this.mlayoutId=layoutId;
        this.list=list;
    }
    @Override
    protected void convert(ViewHolder holder, final Xbanners.TuijianBean.ListBean  list, final int position) {
        holder.setText(R.id.tv_tuijian,list.title);
        holder.setText(R.id.tv1_tuijian,"¥"+list.price);
        ImageView iv = holder.getView(R.id.iv_tuijian);
        String images = list.images;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, XiangqingActivity.class);
                intent.putExtra("pid",list.pid);
                intent.putExtra("url",list.detailUrl);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(context,parent,mlayoutId);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int item_list = R.layout.item_list;
        return item_list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, final View itemView) {
        super.onViewHolderCreated(holder, itemView);

    }


}
