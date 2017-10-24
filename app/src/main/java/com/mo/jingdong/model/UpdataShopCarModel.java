package com.mo.jingdong.model;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/18.
 */

public class UpdataShopCarModel {

    public void updata(String uid,String sellerid,String pid,String num,String selected){
        Map<String, String> params=new HashMap<String, String>();
        params.put("uid", String.valueOf(uid));
        params.put("sellerid",sellerid);
        params.put("pid",pid);
        params.put("num", num);
        params.put("selected",selected);
        NetRequest.Call(API.UPDATA, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                updataShopcar.onFailure(call, e);
            }
            @Override
            public void onResponse(Call call, Response response) {
                if(response!=null){
                    updataShopcar.onSuccess("更改成功");
                }else{
                    updataShopcar.onShibai("更改失败");
                }

            }
        });
    }

    public void setUpdataShopcar(UpdataShopCarModel.updataShopcar updataShopcar) {
        this.updataShopcar = updataShopcar;
    }

    private updataShopcar updataShopcar;
    public interface updataShopcar{
        void onFailure(Call call, IOException e);
        void onSuccess(String msg);
        void onShibai(String msg);
    }
}
