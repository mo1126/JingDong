package com.mo.jingdong.view;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/13.
 */

public interface HuanNCView {

    void onFailure(Call call, IOException e);
    void onXiuGaiSuccess(String msg);
    void onXiuGaiFailure(String msg);
}
