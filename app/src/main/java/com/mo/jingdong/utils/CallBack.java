package com.mo.jingdong.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/8.
 */

public interface CallBack {
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response);
}
