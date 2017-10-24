package com.mo.jingdong.view;

import com.mo.jingdong.entity.GetOrderBean;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/22.
 */

public interface GetOrderView {
    void onFailure(String msg);
    void onGetOrderSuccess(GetOrderBean getOrderBean);
    void onGetOrderFailure(String msg);

}
