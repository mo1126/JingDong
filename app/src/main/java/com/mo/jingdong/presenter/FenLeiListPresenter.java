package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.FenLeiListBean;
import com.mo.jingdong.entity.SousuoBean;
import com.mo.jingdong.model.FenleiListModel;
import com.mo.jingdong.view.FenleiListView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/16.
 */

public class FenLeiListPresenter implements FenleiListModel.FenleiList, FenleiListModel.getSousuo {
    private FenleiListModel fenleiListModel;
    private FenleiListView fenleiListView;
    private Context context;

    public FenLeiListPresenter(FenleiListView fenleiListView, Context context) {
        this.fenleiListView = fenleiListView;
        this.context = context;
        this.fenleiListModel=new FenleiListModel();
        fenleiListModel.setFenleiList(this);
        fenleiListModel.setGetSousuo(this);
    }
    public void getData(String pscid,String page,String sort){
        fenleiListModel.FenleiListRequest(API.PRODUCTS,pscid,page,sort);
    }
    public void getSousuo(String keywords,String page,String sort){
        fenleiListModel.getSousuo(API.SOUSUO,keywords,page,sort);
    }

    @Override
    public void onFailure(Call call, IOException e) {
            fenleiListView.onFailure("网络请求异常");
    }

    @Override
    public void onGetSousuoSuccess(SousuoBean getUserInfoBean) {
        fenleiListView.onGetSousuoSuccess(getUserInfoBean);
    }

    @Override
    public void onGetSousuoFailure(String msg) {
        fenleiListView.onGetSousuoFailure(msg);
    }

    @Override
    public void onFenleiListSuccess(FenLeiListBean fenLeiListBean) {
        fenleiListView.onFenleiListSuccess(fenLeiListBean);
    }

    @Override
    public void onFenleiListFailure(String msg) {
        fenleiListView.onFenleiListFailure(msg);
    }
}
