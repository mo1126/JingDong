package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.CreateOrderBean;
import com.mo.jingdong.entity.ShopCarsBean;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/17.
 */

public class ShopCarModel {

    public void getCars(String url,String uid){

        Map<String, String> params=new HashMap<>();
        params.put("uid",uid);
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                getCarsLinsener.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    if(string.equals("null")){
                        return;
                    }else{
                        Gson gson=new Gson();
                        ShopCarsBean shopCarsBean = gson.fromJson(string, ShopCarsBean.class);
                        if("0".equals(shopCarsBean.code)){
                            getCarsLinsener.getCarsSuccess(shopCarsBean);
                        }else{
                            getCarsLinsener.getCarsFailure(shopCarsBean.msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void CreateOrder(String uid,String price){
        Map<String, String> params =new HashMap<>();
        params.put("uid",uid);
        params.put("price",price);
        NetRequest.Call(API.CREATEORDER, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnCreateOrder.onFailure(call, e);
            }
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    CreateOrderBean createOrderBean = gson.fromJson(string, CreateOrderBean.class);

                    if(createOrderBean.code.equals("0")){
                        OnCreateOrder.onCreateSuccess(createOrderBean.msg);
                    }else{
                        OnCreateOrder.onCreateFailure(createOrderBean.msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private OnCreateOrder OnCreateOrder;

    public void setOnCreateOrder(ShopCarModel.OnCreateOrder onCreateOrder) {
        OnCreateOrder = onCreateOrder;
    }

    public interface OnCreateOrder{
        void onFailure(Call call, IOException e);
        void onCreateSuccess(String msg);
        void onCreateFailure(String msg);
    }
    private getCarsLinsener getCarsLinsener;

    public void setGetCarsLinsener(ShopCarModel.getCarsLinsener getCarsLinsener) {
        this.getCarsLinsener = getCarsLinsener;
    }

    public interface getCarsLinsener{
        void onFailure(Call call, IOException e);
        void getCarsSuccess(ShopCarsBean shopCarsBean);
        void getCarsFailure(String msg);
    }
}
