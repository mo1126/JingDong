package com.mo.jingdong.view;

import com.mo.jingdong.entity.GetUserInfoBean;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/12.
 */

public interface GetInfoView {
    void onFailure(Call call, IOException e);
    void onGetInfoSuccess(GetUserInfoBean getUserInfoBean);
    void onGetInfoFailure(String msg);
}
