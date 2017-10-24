package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.ShangpinBean;
import com.mo.jingdong.model.ShangpinModel;
import com.mo.jingdong.view.ShangpinView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/17.
 */

public class ShangpinPresenter implements ShangpinModel.Xiangqing{
    private ShangpinModel shangpinModel;
    private ShangpinView shangpinView;
    private Context context;

    public ShangpinPresenter(ShangpinView shangpinView, Context context) {
        this.shangpinView = shangpinView;
        this.context = context;
        this.shangpinModel=new ShangpinModel();
        shangpinModel.setXiangqing(this);
    }
    public void getXiangqing(String pid){
        shangpinModel.getXiangqing(API.XIANGQING,pid);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        shangpinView.onFailure("网络请求有误");
    }

    @Override
    public void onXiangqingSuccess(ShangpinBean shangpinBean) {
        shangpinView.onXiangqingSuccess(shangpinBean);
    }

    @Override
    public void onXiangqingFailure(String msg) {
        shangpinView.onXiangqingFailure(msg);
    }
}
