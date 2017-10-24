package com.mo.jingdong.view;

import com.mo.jingdong.entity.LoginBean;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/12.
 */

public interface LoginView {
    void onFailure(Call call, IOException e);
    void onLoginSuccess(LoginBean loginBean);
    void onLoginFailure(String msg);
}
