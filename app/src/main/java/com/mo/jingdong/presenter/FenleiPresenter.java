package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.model.FenleiModel;
import com.mo.jingdong.view.FenleiView;
import com.mo.jingdong.view.MainView;

import java.util.List;

/**
 * Created by Mo on 2017/10/10.
 */

public class FenleiPresenter implements FenleiModel.onFenleiSuccess{
        private FenleiModel fenleiModel;
        private FenleiView fenleiView;
        private Context context;

    public FenleiPresenter(FenleiView fenleiView, Context context) {
        this.fenleiView = fenleiView;
        this.context = context;
        fenleiModel=new FenleiModel();
        fenleiModel.setOnFenleiSuccess(this);
    }

    public void getdata(){
        fenleiModel.RequestFenlei();
    }
    @Override
    public void fenleiSuccess(List<ShouyeFenlei.DataBean> data) {
        fenleiView.fenleiSuccess(data);
    }

    @Override
    public void fenleiFailure(String msg) {
        fenleiView.fenleiFailure(msg);
    }

    @Override
    public void onFailure(String msg) {
        fenleiView.onFailure(msg);
    }
}
