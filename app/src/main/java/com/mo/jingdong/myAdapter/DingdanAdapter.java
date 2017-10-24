package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.jingdong.PayActivity;
import com.mo.jingdong.R;
import com.mo.jingdong.entity.GetOrderBean;
import com.mo.jingdong.presenter.UpdateOrderPresenter;
import com.mo.jingdong.view.UpdataOrderView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/22.
 */

public class DingdanAdapter extends RecyclerView.Adapter<DingdanAdapter.DingDanViewHolder> implements UpdataOrderView {
    private Context context;
    private List<GetOrderBean.DataBean> data;
    private final UpdateOrderPresenter presenter;
    private final int uid;

    public DingdanAdapter(Context context, List<GetOrderBean.DataBean> data) {
        this.context = context;
        this.data = data;
        SharedPreferences sp = context.getSharedPreferences("uid", Context.MODE_PRIVATE);
        uid = sp.getInt("uid", 0);
        presenter = new UpdateOrderPresenter(DingdanAdapter.this,context);
    }

    @Override
    public DingDanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dingdan_list, null);
        DingDanViewHolder dingDanViewHolder=new DingDanViewHolder(inflate);
        return dingDanViewHolder;
    }

    @Override
    public void onBindViewHolder(DingDanViewHolder holder, final int position) {
        holder.tv_lb_time.setText("创建时间:"+data.get(position).createtime);
        holder.tv_lb_price.setText("价格:"+data.get(position).price+"");
        holder.orderid.setText("订单号:"+data.get(position).orderid+"");
        if(data.get(position).status==0){
            holder.tv_st.setText("待处理");
            holder.iv.setVisibility(View.INVISIBLE);
        }else if(data.get(position).status==1){
            holder.tv_st.setText("已支付");
            holder.iv.setImageResource(R.drawable.over);
            holder.iv.setVisibility(View.VISIBLE);
        }else{
            holder.iv.setImageResource(R.drawable.quxiao);
            holder.tv_st.setText("已取消");
            holder.iv.setVisibility(View.VISIBLE);
        }
        holder.t_qunding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( data.get(position).status==0){
                    context.startActivity(new Intent(context, PayActivity.class));
                    data.get(position).status=1;
                    presenter.updataorder(String.valueOf(uid),String.valueOf(1), String.valueOf(data.get(position).orderid));
                }else if(data.get(position).status==1){
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "订单已支付", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "订单已取消", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        holder.t_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( data.get(position).status==0){
                    data.get(position).status=2;
                    presenter.updataorder(String.valueOf(uid),String.valueOf(2), String.valueOf(data.get(position).orderid));
                }else if(data.get(position).status==1){
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "订单已支付", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "订单已取消", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onUpdataSuccess(String msg) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onUpdataFailure(String msg) {

    }

    public class DingDanViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_st;
        private TextView tv_lb_time;
        private TextView tv_lb_price;
        private Button t_quxiao;
        private Button t_qunding;
        private TextView orderid;
        private ImageView iv;
        public DingDanViewHolder(View itemView) {
            super(itemView);
            tv_st=itemView.findViewById(R.id.tv_st);
            tv_lb_time=itemView.findViewById(R.id.tv_lb_time);
            tv_lb_price=itemView.findViewById(R.id.tv_lb_price);
            t_quxiao=itemView.findViewById(R.id.t_quxiao);
            t_qunding=itemView.findViewById(R.id.t_qunding);
            orderid=itemView.findViewById(R.id.ordrerid);
            iv=itemView.findViewById(R.id.over);
        }
    }
}
