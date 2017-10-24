package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.model.UpdataShopCarModel;
import com.mo.jingdong.view.UpdataShopcarView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/18.
 */

public class UpdataShopcarPresenter implements UpdataShopCarModel.updataShopcar {

    private UpdataShopCarModel updataShopCarModel;
    private UpdataShopcarView updataShopcarView;
    private Context context;

    public UpdataShopcarPresenter(UpdataShopcarView updataShopcarView, Context context) {
        this.updataShopcarView = updataShopcarView;
        this.context = context;
        updataShopCarModel=new UpdataShopCarModel();
        updataShopCarModel.setUpdataShopcar(this);
    }
    public void updata(String uid,String sellerid,String pid,String num,String selected){
        updataShopCarModel.updata(uid,sellerid,pid,num,selected);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        updataShopcarView.onFailure("网络请求有误");
    }

    @Override
    public void onSuccess(String msg) {
        updataShopcarView.onSuccess(msg);
    }

    @Override
    public void onShibai(String msg) {
        updataShopcarView.onShibai(msg);
    }
}
