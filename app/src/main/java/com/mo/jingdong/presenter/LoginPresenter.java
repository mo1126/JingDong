package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.LoginBean;
import com.mo.jingdong.model.LoginModel;
import com.mo.jingdong.view.LoginView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/12.
 */

public class LoginPresenter implements LoginModel.loginModel{
    private LoginModel loginModel;
    private LoginView loginView;
    private Context context;

    public LoginPresenter(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
        loginModel=new LoginModel();
        loginModel.setLoginModel(this);
    }
    public void getData(String mobile,String pwd){
        loginModel.login(API.LOGIN,mobile,pwd);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        loginView.onFailure(call, e);
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        loginView.onLoginSuccess(loginBean);
    }

    @Override
    public void onLoginFailure(String msg) {
        loginView.onLoginFailure(msg);
    }
}
