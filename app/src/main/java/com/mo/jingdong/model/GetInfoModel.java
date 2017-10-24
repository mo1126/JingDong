package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.entity.GetUserInfoBean;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/12.
 */

public class GetInfoModel {
    private getUserinfo getUserinfo;

    public void setGetUserinfo(GetInfoModel.getUserinfo getUserinfo) {
        this.getUserinfo = getUserinfo;
    }

    public void getUserInfo(String url, int uid){

        Map<String, String> params=new HashMap<>();
        params.put("uid",uid+"");
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                getUserinfo.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    if(string!=null){
                        Gson gson=new Gson();
                        GetUserInfoBean getUserInfoBean = gson.fromJson(string, GetUserInfoBean.class);
                        if("0".equals(getUserInfoBean.code)){
                            getUserinfo.onGetInfoSuccess(getUserInfoBean);
                        }else{
                            getUserinfo.onGetInfoFailure(getUserInfoBean.msg);
                        }
                    }else{
                        return;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface getUserinfo{
        void onFailure(Call call, IOException e);
        void onGetInfoSuccess(GetUserInfoBean getUserInfoBean);
        void onGetInfoFailure(String msg);
    }
}
