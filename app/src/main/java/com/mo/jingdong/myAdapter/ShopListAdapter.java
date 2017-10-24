package com.mo.jingdong.myAdapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShopCarsBean;

import com.mo.jingdong.presenter.ShopCarsPresenter;
import com.mo.jingdong.presenter.UpdataShopcarPresenter;
import com.mo.jingdong.utils.AmountView;

import com.mo.jingdong.view.ShopCarsView;
import com.mo.jingdong.view.UpdataShopcarView;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mo on 2017/10/18.
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder>  implements UpdataShopcarView, ShopCarsView {

    private Context context;
    private List<ShopCarsBean.DataBean.ListBean> list;
    private final int uid;
    private final UpdataShopcarPresenter presenter;
    private isSelect onSelect;
    private int sum;
    private TextView sumprice;
    private double price;
    private ShopCarsPresenter shopCarsPresenter;

    public void setIsSelect(ShopListAdapter.isSelect onSelect) {
        this.onSelect = onSelect;
    }

    public ShopListAdapter(Context context, List<ShopCarsBean.DataBean.ListBean> list,TextView sumprice) {
        this.context = context;
        this.list = list;
        this.sumprice=sumprice;
        SharedPreferences sp = context.getSharedPreferences("uid", Context.MODE_PRIVATE);
        uid = sp.getInt("uid", 0);
        presenter = new UpdataShopcarPresenter(this,context);
        shopCarsPresenter = new ShopCarsPresenter(ShopListAdapter.this,context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_list_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.shop_name.setText(list.get(position).title);
        holder.shop_price.setText(list.get(position).bargainPrice+"");
        int selected = list.get(position).selected;
        if(selected==0){
            holder.check_list.setChecked(false);
            onSelect.sumselect(sum);
            presenter.updata(String.valueOf(uid),String.valueOf(list.get(position).sellerid),String.valueOf(list.get(position).pid),String.valueOf( list.get(position).num), String.valueOf(0));
        }else{
            holder.check_list.setChecked(true);
            sum++;
            onSelect.sumselect(sum);
            presenter.updata(String.valueOf(uid),String.valueOf(list.get(position).sellerid),String.valueOf(list.get(position).pid),String.valueOf( list.get(position).num), String.valueOf(1));
        }
        setPrice();
        String images = list.get(position).images;
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.shop_iv);
        holder.jiajian.setGoods_storage(10);
        holder.jiajian.setnum(list.get(position).num);
        holder.jiajian.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                presenter.updata(String.valueOf(uid),String.valueOf(list.get(position).sellerid),String.valueOf(list.get(position).pid),String.valueOf(amount), String.valueOf(1));
                holder.check_list.setChecked(true);
                list.get(position).num=amount;
                setPrice();
            }
        });
        holder.check_list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    presenter.updata(String.valueOf(uid),String.valueOf(list.get(position).sellerid),String.valueOf(list.get(position).pid),String.valueOf( list.get(position).num), String.valueOf( 1));
                    list.get(position).selected = 1;
                    sum++;
                }else{
                    presenter.updata(String.valueOf(uid),String.valueOf(list.get(position).sellerid),String.valueOf(list.get(position).pid),String.valueOf( list.get(position).num), String.valueOf(0));
                   list.get(position).selected=0;
                   sum--;
                }
                onSelect.sumselect(sum);
                setPrice();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemLongClickListener(position);
                System.out.println("child  长按点击了"+position+"             size"+list.size());
                return false;
            }
        });
    }
    public void setPrice(){
        shopCarsPresenter.getShopCar(String.valueOf(uid));
    }
    private onItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(ShopListAdapter.onItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface onItemLongClickListener{
        void onItemLongClickListener(int childposition);
    }
    public interface isSelect{
        void sumselect(int sum);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onFailure(String msg) {
        //System.out.println(msg+"*********************");
    }

    @Override
    public void getCarsSuccess(ShopCarsBean shopCarsBean) {
        double sum=0;
        int count=0;
        for (ShopCarsBean.DataBean dataBean : shopCarsBean.data) {
                for (ShopCarsBean.DataBean.ListBean listBean : dataBean.list) {
                    if(listBean.selected==1){
                        sum+= listBean.num * listBean.bargainPrice;
                        count++;
                    }
                }
        }
        final double finalSum = sum;
        new Handler(context.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                DecimalFormat decimalFormat = new DecimalFormat("#0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
                String price_num = decimalFormat.format(finalSum);// format 返回的是字符串
                sumprice.setText("合计: ¥"+price_num +"");
            }
        },1000);

    }

    @Override
    public void getCarsFailure(String msg) {

    }

    @Override
    public void onCreateSuccess(String msg) {

    }

    @Override
    public void onCreateFailure(String msg) {

    }

    @Override
    public void onSuccess(String msg) {
    }
    @Override
    public void onShibai(String msg) {
        //System.out.println(msg+"*********************");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox check_list;
        private ImageView shop_iv;
        private TextView shop_name;
        private TextView shop_price;
        private AmountView jiajian;
        public ViewHolder(View itemView) {
            super(itemView);
            check_list=itemView.findViewById(R.id.check_list);
            shop_iv=itemView.findViewById(R.id.shop_iv);
            shop_name=itemView.findViewById(R.id.shop_name);
            shop_price=itemView.findViewById(R.id.shop_price);
            jiajian=itemView.findViewById(R.id.jiajian);
        }
    }
}
