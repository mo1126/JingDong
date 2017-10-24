package com.mo.jingdong.view;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/22.
 */

public interface UpdataOrderView {
    void onFailure(Call call, IOException e);
    void onUpdataSuccess(String msg);
    void onUpdataFailure(String msg);
}
