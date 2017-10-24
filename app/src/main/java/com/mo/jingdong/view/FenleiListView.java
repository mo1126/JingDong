package com.mo.jingdong.view;

import com.mo.jingdong.entity.FenLeiListBean;
import com.mo.jingdong.entity.SousuoBean;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/16.
 */

public interface FenleiListView {
    void onFailure(String msg);
    void onFenleiListSuccess(FenLeiListBean fenLeiListBean);
    void onFenleiListFailure(String msg);


    void onGetSousuoSuccess(SousuoBean getUserInfoBean);
    void onGetSousuoFailure(String msg);
}
