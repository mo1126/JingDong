package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/10.
 */

public class FenleiModel {

    private onFenleiSuccess onFenleiSuccess;

    public void setOnFenleiSuccess(onFenleiSuccess onFenleiSuccess) {
        this.onFenleiSuccess = onFenleiSuccess;
    }
    public void RequestFenlei(){

        Map<String, String> map=new HashMap<>();
        NetRequest.Call(API.FENLEI, map, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                onFenleiSuccess.onFailure("网络请求错误");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    ShouyeFenlei fenlei = gson.fromJson(string, ShouyeFenlei.class);
                    if("0".equals(fenlei.code)){
                        List<ShouyeFenlei.DataBean> data = fenlei.data;
                        onFenleiSuccess.fenleiSuccess(data);
                    }else{
                        onFenleiSuccess.fenleiFailure(fenlei.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public interface onFenleiSuccess{
        void fenleiSuccess(List<ShouyeFenlei.DataBean> data);
        void fenleiFailure(String msg);
        void onFailure(String msg);
    }
}
