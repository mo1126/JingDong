package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.entity.UpOrderBean;
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

public class UpdateOrderModel {

    public void updateOrder(String url, String uid, final String status, String orderId){
        Map<String, String> params=new HashMap<>();
        params.put("uid",uid);
        params.put("status",status);
        params.put("orderId",orderId);
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                onUpdateOrder.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    UpOrderBean upOrderBean = gson.fromJson(string, UpOrderBean.class);
                    if("0".equals(upOrderBean.code)){
                        onUpdateOrder.onUpdataSuccess(upOrderBean.msg);
                    }else{
                        onUpdateOrder.onUpdataFailure(upOrderBean.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private onUpdateOrder onUpdateOrder;

    public void setOnUpdateOrder(UpdateOrderModel.onUpdateOrder onUpdateOrder) {
        this.onUpdateOrder = onUpdateOrder;
    }

    public interface onUpdateOrder{
        void onFailure(Call call, IOException e);
        void onUpdataSuccess(String msg);
        void onUpdataFailure(String msg);
    }
}
