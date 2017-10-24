package com.mo.jingdong.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShopCarsBean;
import com.mo.jingdong.myAdapter.ShopCarsAdapter;
import com.mo.jingdong.presenter.ShopCarsPresenter;
import com.mo.jingdong.view.ShopCarsView;

import java.util.List;

/**
 * Created by Mo on 2017/10/11.
 */

public class GouWuCheFragment extends Fragment implements View.OnClickListener, ShopCarsView {

    private View view;
    private TextView sumprice;
    private RecyclerView rc_gouwuche;
    private Button pay;
    private TextView bianji;
    private CheckBox allselect;
    private ShopCarsPresenter presenter;
    private int uid;
    private ShopCarsAdapter myadapter;
    private SwipeRefreshLayout swf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.gouwuche, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        swf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getShopCar(String.valueOf(uid));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getContext().getSharedPreferences("uid", Context.MODE_PRIVATE);
        uid = sp.getInt("uid", 0);
        presenter = new ShopCarsPresenter(this,getContext());
        if(uid!=0){
            presenter.getShopCar(String.valueOf(uid));
        }
    }

    private void initView() {
        allselect = view.findViewById(R.id.allselect);
        sumprice = view.findViewById(R.id.sumprice);
        rc_gouwuche = view.findViewById(R.id.rc_gouwuche);
        pay = view.findViewById(R.id.pay);
        bianji = view.findViewById(R.id.bianji);
        swf = view.findViewById(R.id.gouwuche_sr);
        bianji.setOnClickListener(this);
        pay.setOnClickListener(this);
        allselect.setOnCheckedChangeListener(null);
    }

    @Override
    public void onClick(View view) {
          switch (view.getId()){
              case R.id.bianji:break;
              case R.id.pay:
                  String s = sumprice.getText().toString();
                  String[] s1= s.split("¥");
                  System.out.println(s1[1]+"这是总价");
                    presenter.createOrder(String.valueOf(uid),s1[1]);
                  break;
          }
    }


    @Override
    public void onFailure(final String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void getCarsSuccess(final ShopCarsBean shopCarsBean) {

        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swf.setRefreshing(false);
                    LinearLayoutManager lm=new LinearLayoutManager(getContext());
                    rc_gouwuche.setLayoutManager(lm);
                        myadapter = new ShopCarsAdapter(getContext(),shopCarsBean.data,sumprice);
                        rc_gouwuche.setAdapter(myadapter);
                        allselect.setChecked(false);

                        allselect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(allselect.isChecked()){
                                for (ShopCarsBean.DataBean dataBean : shopCarsBean.data) {
                                    for (ShopCarsBean.DataBean.ListBean listBean : dataBean.list) {
                                        listBean.selected=1;
                                    }
                                }
                            }else{
                                allselect.setChecked(false);
                                for (ShopCarsBean.DataBean dataBean : shopCarsBean.data) {
                                    for (ShopCarsBean.DataBean.ListBean listBean : dataBean.list) {
                                        listBean.selected=0;
                                    }
                                }
                            }
                            myadapter.notifyDataSetChanged();
                        }
                    });
                    myadapter.setOnAllSelect(new ShopCarsAdapter.onAllSelect() {
                        @Override
                        public void isAllSelect(int sum) {
                            allselect.setSelected(true);
                            System.out.println("是否全选"+sum+"/"+shopCarsBean.data.size());
                            if(sum==shopCarsBean.data.size()){
                                 allselect.setChecked(true);
                                System.out.println("设置全选1");
                            }else{
                                System.out.println("设置全选");
                                allselect.setChecked(false);
                            }
                        }
                    });

                    Toast.makeText(getActivity(), "获取购物车成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void getCarsFailure(final String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onCreateSuccess(final String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onCreateFailure(final String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
