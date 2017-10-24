package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mo.jingdong.R;
import com.mo.jingdong.XiangqingActivity;
import com.mo.jingdong.entity.Xbanners;

import java.util.List;

/**
 * Created by Mo on 2017/10/9.
 */

public class MiaoShaAdapter extends RecyclerView.Adapter<MiaoShaAdapter.ViewHolder>{

    private Context context;
    private Xbanners.MiaoshaBean miaosha;
    private final List<Xbanners.MiaoshaBean.ListBeanX> list;

    private View view;



    public MiaoShaAdapter(Context context, Xbanners.MiaoshaBean miaosha) {
        this.context = context;
        this.miaosha = miaosha;
        list = miaosha.list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.miaoshaitem, null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText("¥"+list.get(position).price);
        holder.tv1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv1.setText("¥"+list.get(position).bargainPrice);

        String images = list.get(position).images;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, XiangqingActivity.class);
                intent.putExtra("pid",list.get(position).pid);
                intent.putExtra("url",list.get(position).detailUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;
        private TextView tv1;

        public ViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_miaosha);
            tv=itemView.findViewById(R.id.tv_miaosha);
            tv1=itemView.findViewById(R.id.tv1_miaosha);

        }
    }


}
