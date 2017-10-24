package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.entity.GetOrderBean;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/22.
 */

public class GetOrders {

    public void getOrder(String url,String uid){
        Map<String, String> params=new HashMap<>();
        params.put("uid",uid);
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                onGetOrder.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    GetOrderBean getOrderBean = gson.fromJson(string, GetOrderBean.class);
                    if("0".equals(getOrderBean.code)){
                        onGetOrder.onGetOrderSuccess(getOrderBean);
                    }else{
                        onGetOrder.onGetOrderFailure(getOrderBean.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private onGetOrder onGetOrder;

    public void setOnGetOrder(GetOrders.onGetOrder onGetOrder) {
        this.onGetOrder = onGetOrder;
    }

    public interface onGetOrder{
        void onFailure(Call call, IOException e);
        void onGetOrderSuccess(GetOrderBean getOrderBean);
        void onGetOrderFailure(String msg);
    }
}
