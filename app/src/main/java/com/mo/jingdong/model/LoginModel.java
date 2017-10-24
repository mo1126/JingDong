package com.mo.jingdong.model;

import android.content.Context;

import com.google.gson.Gson;
import com.mo.jingdong.entity.LoginBean;
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

public class LoginModel {

    private loginModel loginModel;

    public void setLoginModel(LoginModel.loginModel loginModel) {
        this.loginModel = loginModel;
    }

    public void login(String url,String mobile,String pwd){

        Map<String, String> params=new HashMap<>();
        params.put("mobile",mobile);
        params.put("password",pwd);
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginModel.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    LoginBean loginBean = gson.fromJson(string, LoginBean.class);
                    if("0".equals(loginBean.code)){
                        loginModel.onLoginSuccess(loginBean);
                    }else{
                        loginModel.onLoginFailure(loginBean.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public interface loginModel{
        void onFailure(Call call, IOException e);
        void onLoginSuccess(LoginBean loginBean);
        void onLoginFailure(String msg);
    }


}
