package com.mo.jingdong.view;

import com.mo.jingdong.entity.ZiFenlei;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/11.
 */

public interface ZiFenleiView {
    void onFenleiSuccess(List<ZiFenlei.DataBean> data);
    void onFenleiFailure(String msg);
    void onFailure(Call call, IOException e);
}
