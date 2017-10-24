package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShopCarsBean;
import com.mo.jingdong.presenter.DeletePresenter;
import com.mo.jingdong.presenter.ShopCarsPresenter;
import com.mo.jingdong.presenter.UpdataShopcarPresenter;
import com.mo.jingdong.view.DeleteView;
import com.mo.jingdong.view.ShopCarsView;
import com.mo.jingdong.view.UpdataShopcarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mo on 2017/10/18.
 */

public class ShopCarsAdapter  extends RecyclerView.Adapter<ShopCarsAdapter.MyViewHolder> implements UpdataShopcarView, DeleteView {

    private Context context;
    private List<ShopCarsBean.DataBean> data;

    private ShopListAdapter itemAdapter;
    private int allSelectCount;
    private ShopCarsPresenter ShopCarsPresenter;
    private Runnable runnable;
    private final List<Boolean> list;
    private TextView sumprice;
    private final DeletePresenter deletePresenter;
    private final int uid;

    public ShopCarsAdapter(Context context, List<ShopCarsBean.DataBean> data,TextView sumprice ) {
        this.context = context;
        this.data = data;
        this.sumprice=sumprice;
        list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).sele);
        }
        SharedPreferences sp = context.getSharedPreferences("uid", Context.MODE_PRIVATE);
        uid = sp.getInt("uid", 0);
        deletePresenter = new DeletePresenter(context,ShopCarsAdapter.this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_shopcar_item, null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.isselect.setOnCheckedChangeListener(null);
        int a=0;
        for (ShopCarsBean.DataBean.ListBean listBean : data.get(holder.getLayoutPosition()).list) {
            if(listBean.selected==1){
                a++;
            }
        }
        if(a==data.get(holder.getLayoutPosition()).list.size()){
            holder.isselect.setChecked(true);
            list.set(holder.getLayoutPosition(),true);
        }else{
            holder.isselect.setChecked(false);
            list.set(holder.getLayoutPosition(),false);
        }
        holder.title.setText(data.get(holder.getLayoutPosition()).sellerName);
        LinearLayoutManager lm=new LinearLayoutManager(context);
        holder.rc_shoplist.setLayoutManager(lm);
            itemAdapter = new ShopListAdapter(context,data.get(holder.getLayoutPosition()).list, sumprice);
            holder.rc_shoplist.setAdapter(itemAdapter);
            itemAdapter.setIsSelect(new ShopListAdapter.isSelect() {
            @Override
            public void sumselect(int sum) {
                if(sum==data.get(holder.getLayoutPosition()).list.size()){
                    holder.isselect.setChecked(true);
                    list.set(holder.getLayoutPosition(),true);
                    getSelect();

                }else{
                    holder.isselect.setChecked(false);
                    list.set(holder.getLayoutPosition(),false);
                    getSelect();

                }
            }
        });
        holder.isselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < data.get(holder.getLayoutPosition()).list.size(); i++) {
                    if(holder.isselect.isChecked()){
                        data.get(holder.getLayoutPosition()).list.get(i).selected=1;
                        list.set(holder.getLayoutPosition(),true);
                    }else{
                        data.get(holder.getLayoutPosition()).list.get(i).selected=0;
                        list.set(holder.getLayoutPosition(),false);
                     }
                }
                notifyDataSetChanged();
                getSelect();
            }
        });
        itemAdapter.setOnItemLongClickListener(new ShopListAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClickListener(final int childposition) {
                AlertDialog.Builder al=new AlertDialog.Builder(context);
                al.setMessage("是否删除当前商品");
                al.setTitle("删除");
                al.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                al.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("list  size"+data.get(position).list.size()+"childposition"+childposition );
                        deletePresenter.DeleteShop(String.valueOf(uid), String.valueOf(data.get(holder.getLayoutPosition()).list.get(childposition).pid));
                        data.get(holder.getLayoutPosition()).list.remove(childposition);
                        if(data.get(holder.getLayoutPosition()).list.size()==0){
                            data.remove(holder.getLayoutPosition());
                        }
                        notifyDataSetChanged();
                    }
                });
                al.create().show();
                data.get(holder.getLayoutPosition()).list.get(childposition);
                System.out.println("点击了"+data.get(holder.getLayoutPosition()).list.get(childposition).title);
            }
        });

    }

    public void getSelect(){
        allSelectCount=0;
        for (Boolean aBoolean : list) {
            if(aBoolean){
                allSelectCount++;
            }
        }

        onAllSelect.isAllSelect(allSelectCount);
    }
    public void setOnAllSelect(ShopCarsAdapter.onAllSelect onAllSelect) {
        this.onAllSelect = onAllSelect;
    }

    private onAllSelect onAllSelect;

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDeleteSuccess(String msg) {
        System.out.println(msg);

    }

    @Override
    public void onDeleteFailure(String msg) {

    }

    @Override
    public void onSuccess(String msg) {
    }

    @Override
    public void onShibai(String msg) {

    }

    public interface onAllSelect{
        void isAllSelect(int sum);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CheckBox isselect;
        private TextView title;
        private RecyclerView rc_shoplist;
        public MyViewHolder(View itemView) {
            super(itemView);
            isselect=itemView.findViewById(R.id.dianpu_isselect);
            title=itemView.findViewById(R.id.dianpu_name);
            rc_shoplist=itemView.findViewById(R.id.rc_gouwuche_item);
        }
    }
}
