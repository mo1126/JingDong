package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.entity.ShangpinBean;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/17.
 */

public class ShangpinModel {

    public void getXiangqing(String url,String pid){
        Map<String, String> params=new HashMap<>();
        params.put("pid",pid);
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                Xiangqing.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    ShangpinBean shangpinBean = gson.fromJson(string, ShangpinBean.class);
                    if("0".equals(shangpinBean.code)){
                        Xiangqing.onXiangqingSuccess(shangpinBean);
                    }else{
                        Xiangqing.onXiangqingFailure(shangpinBean.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private Xiangqing Xiangqing;

    public void setXiangqing(ShangpinModel.Xiangqing xiangqing) {
        Xiangqing = xiangqing;
    }

    public interface Xiangqing{
        void onFailure(Call call, IOException e);
        void onXiangqingSuccess(ShangpinBean shangpinBean);
        void onXiangqingFailure(String msg);
    }
}
