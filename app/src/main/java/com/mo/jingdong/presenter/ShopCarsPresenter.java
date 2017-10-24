package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.ShopCarsBean;
import com.mo.jingdong.model.ShopCarModel;
import com.mo.jingdong.view.ShopCarsView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/17.
 */

public class ShopCarsPresenter implements ShopCarModel.getCarsLinsener, ShopCarModel.OnCreateOrder {
    private ShopCarModel shopCarModel;
    private ShopCarsView shopCarsView;
    private Context context;

    public ShopCarsPresenter(ShopCarsView shopCarsView, Context context) {
        this.shopCarsView = shopCarsView;
        this.context = context;
        this.shopCarModel=new ShopCarModel();
        shopCarModel.setGetCarsLinsener(this);
        shopCarModel.setOnCreateOrder(this);
    }

    public void getShopCar(String uid){
        shopCarModel.getCars(API.SHOPCRAS,uid);
    }
    public void createOrder(String uid,String price){
        shopCarModel.CreateOrder(uid,price);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        shopCarsView.onFailure("网络有误");
    }

    @Override
    public void onCreateSuccess(String msg) {
        shopCarsView.onCreateSuccess(msg);
    }

    @Override
    public void onCreateFailure(String msg) {
        shopCarsView.onCreateFailure(msg);
    }

    @Override
    public void getCarsSuccess(ShopCarsBean shopCarsBean) {
        shopCarsView.getCarsSuccess(shopCarsBean);
    }

    @Override
    public void getCarsFailure(String msg) {
        shopCarsView.getCarsFailure(msg);
    }
}
