package com.mo.jingdong.utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/8.
 */

public class NetRequest {

    public static  void Call(String url, Map<String,String>params, final CallBack callBack){
        OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new LogInterceptor());
        okHttpClient.addNetworkInterceptor(new LogInterceptor());
        OkHttpClient build = okHttpClient.build();
        FormBody.Builder builder=new FormBody.Builder();
        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            builder.add(stringStringEntry.getKey(),stringStringEntry.getValue());
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder().post(body).url(url).build();
        build.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(call, response);
            }
        });
    }
}
