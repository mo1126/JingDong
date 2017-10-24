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
import com.mo.jingdong.entity.FenLeiListBean;
import com.mo.jingdong.entity.SousuoBean;

import java.util.List;

/**
 * Created by Mo on 2017/10/16.
 */

public class FenleiListAdapter extends RecyclerView.Adapter<FenleiListAdapter.MyViewHolder>{
    private Context context;
    private List<FenLeiListBean.DataBean> data;
    private Boolean leixing;
    private List<SousuoBean.DataBean> data1;
    private Context context1;



    public FenleiListAdapter(Context context, List<FenLeiListBean.DataBean> data, Boolean leixing) {
        this.context = context;
        this.data = data;
        this.leixing = leixing;
    }
    public FenleiListAdapter(List<SousuoBean.DataBean> data, Boolean leixing,Context context) {
        this.context1=context;
        this.data1 = data;
        this.leixing = leixing;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(leixing){
            if(context!=null){
                view = LayoutInflater.from(context).inflate(R.layout.fenlei_listone,null);
            }else{
                view = LayoutInflater.from(context1).inflate(R.layout.fenlei_listone,null);
            }

        }else{
            if(context!=null){
                view = LayoutInflater.from(context).inflate(R.layout.fenlei_listtwo,null);
            }else{
                view = LayoutInflater.from(context1).inflate(R.layout.fenlei_listtwo,null);
            }

        }
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(data!=null){
            holder.title.setText(data.get(position).title);
            holder.price.setText("¥  "+data.get(position).price);
            String images = data.get(position).images;
            String[] split = images.split("\\|");
            Glide.with(context).load(split[0]).into(holder.iv);
        }else{
            holder.title.setText(data1.get(position).title);
            holder.price.setText("¥ "+data1.get(position).price+"");
            String images = data1.get(position).images;
            String[] split = images.split("\\|");
            if(context!=null){
                Glide.with(context).load(split[0]).into(holder.iv);
            }else{
                Glide.with(context1).load(split[0]).into(holder.iv);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context!=null){
                    Intent intent=new Intent(context, XiangqingActivity.class);
                    intent.putExtra("pid",data.get(position).pid);
                    intent.putExtra("url",data.get(position).detailUrl);
                    context.startActivity(intent);
                }else{
                    Intent intent=new Intent(context1, XiangqingActivity.class);
                    intent.putExtra("pid",data1.get(position).pid);
                    intent.putExtra("url",data1.get(position).detailUrl);
                    context1.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if(data!=null){
            return data.size();
        }else{
            return data1.size();
        }

    }

    public void setgeshi(Boolean geshi) {
        leixing=geshi;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView title;
        private TextView price;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_fenleilist);
            title=itemView.findViewById(R.id.title_fenleilist);
            price=itemView.findViewById(R.id.price_fenleilist);
        }
    }
}
