package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.mo.jingdong.entity.GaiNichengBean;
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

public class GaiNichengModel {

    public void XiugaiReuest(String url,String uid,String nickname){
        Map<String, String> params=new HashMap<>();
        params.put("uid",uid);
        params.put("nickname",nickname);
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                gaiNicheng.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    GaiNichengBean gaiNichengBean = gson.fromJson(string, GaiNichengBean.class);
                    if("0".equals(gaiNichengBean.code)){
                        gaiNicheng.onXiuGaiSuccess(gaiNichengBean.msg);
                    }else{
                        gaiNicheng.onXiuGaiFailure(gaiNichengBean.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private gaiNicheng gaiNicheng;

    public void setGaiNicheng(GaiNichengModel.gaiNicheng gaiNicheng) {
        this.gaiNicheng = gaiNicheng;
    }

    public interface gaiNicheng{
        void onFailure(Call call, IOException e);
        void onXiuGaiSuccess(String msg);
        void onXiuGaiFailure(String msg);
    }
}
