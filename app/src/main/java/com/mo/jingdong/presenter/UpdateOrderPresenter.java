package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.model.UpdateOrderModel;
import com.mo.jingdong.view.UpdataOrderView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/22.
 */

public class UpdateOrderPresenter implements UpdateOrderModel.onUpdateOrder {

    private UpdateOrderModel updateOrderModel;
    private UpdataOrderView updataOrderView;
    private Context context;

    public UpdateOrderPresenter(UpdataOrderView updataOrderView, Context context) {
        this.updataOrderView = updataOrderView;
        this.context = context;
        this.updateOrderModel=new UpdateOrderModel();
        updateOrderModel.setOnUpdateOrder(this);
    }

    public void updataorder(String uid,String statud,String orderid){
        updateOrderModel.updateOrder(API.UPDATAORDER,uid,statud,orderid);
    }
    @Override
    public void onFailure(Call call, IOException e) {
        updataOrderView.onFailure(call, e);
    }

    @Override
    public void onUpdataSuccess(String msg) {
        updataOrderView.onUpdataSuccess(msg);
    }

    @Override
    public void onUpdataFailure(String msg) {
        updataOrderView.onUpdataFailure(msg);
    }
}
