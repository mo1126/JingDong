package com.mo.jingdong;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mo.jingdong.entity.GetOrderBean;
import com.mo.jingdong.myAdapter.DingdanAdapter;
import com.mo.jingdong.presenter.GetOrderPresenter;
import com.mo.jingdong.view.GetOrderView;

import java.util.List;

public class DingdanActivity extends AppCompatActivity implements GetOrderView {

    private RecyclerView rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);
        initView();
        initData();
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("uid", MODE_PRIVATE);
        int uid = sp.getInt("uid", 0);
        GetOrderPresenter presenter=new GetOrderPresenter(this,this);
        presenter.getOrder(String.valueOf(uid));
    }

    private void initView() {
        rc = (RecyclerView) findViewById(R.id.dingdan_rc);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        rc.setLayoutManager(lm);
    }

    @Override
    public void onFailure(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DingdanActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onGetOrderSuccess(final GetOrderBean getOrderBean) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DingdanAdapter myadapter=new DingdanAdapter(DingdanActivity.this,getOrderBean.data);
                    rc.setAdapter(myadapter);
                }
            });
        }
    }

    @Override
    public void onGetOrderFailure(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DingdanActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
