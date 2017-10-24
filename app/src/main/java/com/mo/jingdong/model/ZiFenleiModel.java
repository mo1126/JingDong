package com.mo.jingdong.model;

import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.ZiFenlei;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/11.
 */

public class ZiFenleiModel {

        private mZiFenlei mzifenlei;

    public void setZifenlei(mZiFenlei zifenlei) {
        this.mzifenlei = zifenlei;
    }

    public void RequestZiFenlei(int cid){
        Map<String, String> params=new HashMap<>();
        params.put("cid",cid+"");
        NetRequest.Call(API.ZIFENLEI, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                mzifenlei.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    ZiFenlei ziFenlei = gson.fromJson(string, ZiFenlei.class);
                    List<ZiFenlei.DataBean> data = ziFenlei.data;
                    if("0".equals(ziFenlei.code)){
                        mzifenlei.onFenleiSuccess(data);
                    }else{
                        mzifenlei.onFenleiFailure(ziFenlei.msg);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface mZiFenlei{
        void onFenleiSuccess(List<ZiFenlei.DataBean> data);
        void onFenleiFailure(String msg);
        void onFailure(Call call, IOException e);
    }
}
