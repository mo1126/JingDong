package com.mo.jingdong.view;

import com.mo.jingdong.entity.ShouyeFenlei;

import java.util.List;

/**
 * Created by Mo on 2017/10/10.
 */

public interface FenleiView {
    void fenleiSuccess(List<ShouyeFenlei.DataBean> data);
    void fenleiFailure(String msg);
    void onFailure(String msg);
}
