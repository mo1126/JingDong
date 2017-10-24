package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.GetUserInfoBean;
import com.mo.jingdong.model.GetInfoModel;
import com.mo.jingdong.view.GetInfoView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/12.
 */

public class GetInfoPresenter implements GetInfoModel.getUserinfo {
    private GetInfoModel getInfoModel;
    private GetInfoView getInfoView;
    private Context context;

    public GetInfoPresenter(GetInfoView getInfoView, Context context) {
        this.getInfoView = getInfoView;
        this.context = context;
        getInfoModel=new GetInfoModel();
        getInfoModel.setGetUserinfo(this);
    }
    public void getData(int uid){
        getInfoModel.getUserInfo(API.GETINFO,uid);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        getInfoView.onFailure(call, e);
    }

    @Override
    public void onGetInfoSuccess(GetUserInfoBean getUserInfoBean) {
        getInfoView.onGetInfoSuccess(getUserInfoBean);
    }

    @Override
    public void onGetInfoFailure(String msg) {
        getInfoView.onGetInfoFailure(msg);
    }
}
