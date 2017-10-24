package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShouyeFenlei;

import java.util.List;

/**
 * Created by Mo on 2017/10/10.
 */

public class FenleiLeftAdapter  extends RecyclerView.Adapter<FenleiLeftAdapter.mViewHolder>{

    public Context context;
    public List<ShouyeFenlei.DataBean> data;
    public onItemClick onItemClick;
    private View view;
    private int id;

    public void setOnItemClick(FenleiLeftAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public FenleiLeftAdapter(Context context, List<ShouyeFenlei.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.fenlei_left_item, null);
        mViewHolder mViewHolder=new mViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(position, view);
            }
        });

        if(position==id){
            holder.tv.setSelected(true);
        }else{
            holder.tv.setSelected(false);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class mViewHolder extends ViewHolder{
        public TextView tv;
        public mViewHolder(View itemView) {
            super(itemView);
            tv= itemView.findViewById(R.id.fenlei_left_item);
        }
    }
    public interface onItemClick{
        void onItemClick(int position,View view);
    }

    public void getid(int i){
        id=i;
        notifyDataSetChanged();
    }
}
