package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.entity.Xbanners;
import com.mo.jingdong.model.ShouyeModel;
import com.mo.jingdong.view.MainView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/8.
 */

public class MainPresenter implements ShouyeModel.onSuccess {
    public ShouyeModel mainModel;
    public  MainView mainView;
    public Context context;
    public MainPresenter(Context context, MainView mainView) {
        mainModel = new ShouyeModel();
        this.context=context;
        this.mainView=mainView;
        mainModel.setSuccess(this);
    }

    public void getdata(){
        mainModel.Request();
    }

    @Override
    public void getdata(List<Xbanners.DataBean> data) {
        mainView.setXbanner(data);
    }

    @Override
    public void miaosha(Xbanners.MiaoshaBean miaosha) {
        mainView.setMiaosha(miaosha);
    }

    @Override
    public void tuijian(Xbanners.TuijianBean tuijian) {
        mainView.setTuijian(tuijian);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }


}
