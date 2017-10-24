package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.entity.ZiFenlei;
import com.mo.jingdong.model.FenleiModel;
import com.mo.jingdong.model.ZiFenleiModel;
import com.mo.jingdong.view.ZiFenleiView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/11.
 */

public class ZifenleiPresenter implements ZiFenleiModel.mZiFenlei{
    private ZiFenleiModel ziFenleiModel;
    private ZiFenleiView ziFenleiView;
    private Context context;

    public ZifenleiPresenter(ZiFenleiView ziFenleiView, Context context) {
        this.ziFenleiView = ziFenleiView;
        this.context = context;
        ziFenleiModel=new ZiFenleiModel();
        ziFenleiModel.setZifenlei(this);
    }
    public void getData(int cid){
        ziFenleiModel.RequestZiFenlei(cid);
    }

    @Override
    public void onFenleiSuccess(List<ZiFenlei.DataBean> data) {
        ziFenleiView.onFenleiSuccess(data);
    }

    @Override
    public void onFenleiFailure(String msg) {
        ziFenleiView.onFenleiFailure(msg);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        ziFenleiView.onFailure(call, e);
    }
}
