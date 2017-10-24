package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.model.ShouyeModel;
import com.mo.jingdong.view.SyFenlei1View;

import java.util.List;

/**
 * Created by Mo on 2017/10/9.
 */

public class ShouyeFenleiPresenter implements ShouyeModel.onFenleiSuccess{
    public ShouyeModel mainModel;
    public SyFenlei1View f1View;
    public Context context;

    public ShouyeFenleiPresenter(SyFenlei1View f1View, Context context) {
        this.mainModel = new ShouyeModel();
        this.f1View = f1View;
        this.context = context;
    }
    public void getdata(){
        mainModel.RequestFenlei();
        mainModel.setOnFenleiSuccess(this);
    }

    @Override
    public void fenlei(List<ShouyeFenlei.DataBean> data) {
        f1View.setFenlei(data);
    }
}
