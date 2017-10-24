package com.mo.jingdong.view;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/18.
 */

public interface UpdataShopcarView {

    void onFailure(String msg);
    void onSuccess(String msg);
    void onShibai(String msg);

}
