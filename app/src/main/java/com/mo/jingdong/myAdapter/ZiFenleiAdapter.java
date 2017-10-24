package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mo.jingdong.Fragment.FenleiRightFragment;
import com.mo.jingdong.R;
import com.mo.jingdong.entity.ZiFenlei;

import java.util.List;

/**
 * Created by Mo on 2017/10/11.
 */

public class ZiFenleiAdapter extends RecyclerView.Adapter<ZiFenleiAdapter.mViewHolder>  {

     private Context context;
    private List<ZiFenlei.DataBean> data;

    public ZiFenleiAdapter(Context context, List<ZiFenlei.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fenlei_right_item, null);
        mViewHolder mViewHolder=new mViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.title.setText(data.get(position).name);
        List<ZiFenlei.DataBean.ListBean> list = data.get(position).list;
        GridLayoutManager gm=new GridLayoutManager(context,3);
        holder.rc.setLayoutManager(gm);
        FenleiRightItemAdapter adapter=new FenleiRightItemAdapter(context,list);
        holder.rc.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public RecyclerView rc;
        public mViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.fenlei_right_right_title);
            rc=itemView.findViewById(R.id.fenlei_right_right_rc);
        }
    }
}
