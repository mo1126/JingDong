package com.mo.jingdong.view;

import com.mo.jingdong.entity.ShangpinBean;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/17.
 */

public interface ShangpinView {
    void onFailure(String msg);
    void onXiangqingSuccess(ShangpinBean shangpinBean);
    void onXiangqingFailure(String msg);
}
