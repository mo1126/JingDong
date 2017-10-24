package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.GetOrderBean;
import com.mo.jingdong.model.GetOrders;
import com.mo.jingdong.view.GetOrderView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/22.
 */

public class GetOrderPresenter implements GetOrders.onGetOrder {
    private Context context;
    private GetOrders model;
    private GetOrderView getOrderView;

    public GetOrderPresenter(Context context, GetOrderView getOrderView) {
        this.context = context;
        this.getOrderView = getOrderView;
        model=new GetOrders();
        model.setOnGetOrder(this);
    }

    public void getOrder(String uid){
        model.getOrder(API.GETORDER,uid);
    }
    @Override
    public void onFailure(Call call, IOException e) {
        getOrderView.onFailure("网络有误,请求失败");
    }

    @Override
    public void onGetOrderSuccess(GetOrderBean getOrderBean) {
        getOrderView.onGetOrderSuccess(getOrderBean);
    }

    @Override
    public void onGetOrderFailure(String msg) {
        getOrderView.onGetOrderFailure(msg);
    }
}
