package com.mo.jingdong.view;

import com.mo.jingdong.entity.ShopCarsBean;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/18.
 */

public interface ShopCarsView {
    void onFailure(String msg);
    void getCarsSuccess(ShopCarsBean shopCarsBean);
    void getCarsFailure(String msg);
    void onCreateSuccess(String msg);
    void onCreateFailure(String msg);
}
