package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.entity.Xbanners;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/8.
 */

public class ShouyeModel {

    private onSuccess success;
    private onFenleiSuccess onFenleiSuccess;

    public void setOnFenleiSuccess(onFenleiSuccess onFenleiSuccess) {
        this.onFenleiSuccess = onFenleiSuccess;
    }

    public void setSuccess(onSuccess success) {
        this.success = success;
    }

    public void Request(){
        Map<String, String> map=new HashMap<>();
        NetRequest.Call(API.XBANNERURL, map, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                success.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    Xbanners xbanners = gson.fromJson(string, Xbanners.class);
                    if("0".equals(xbanners.code)){
                        List<Xbanners.DataBean> data = xbanners.data;
                        Xbanners.MiaoshaBean miaosha = xbanners.miaosha;
                        Xbanners.TuijianBean tuijian = xbanners.tuijian;

                        success.getdata(data);
                        success.miaosha(miaosha);
                        success.tuijian(tuijian);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void RequestFenlei(){
        Map<String, String> map=new HashMap<>();
        NetRequest.Call(API.FENLEI, map, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                success.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    ShouyeFenlei fenlei = gson.fromJson(string, ShouyeFenlei.class);
                    if("0".equals(fenlei.code)){
                        List<ShouyeFenlei.DataBean> data = fenlei.data;
                        onFenleiSuccess.fenlei(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public interface onSuccess{
        void getdata(List<Xbanners.DataBean> data);
        void miaosha(Xbanners.MiaoshaBean miaosha);
        void tuijian(Xbanners.TuijianBean tuijian);
        void onFailure(Call call, IOException e);

    }
    public interface onFenleiSuccess{
        void fenlei(List<ShouyeFenlei.DataBean> data);
    }

}
