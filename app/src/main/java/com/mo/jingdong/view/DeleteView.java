package com.mo.jingdong.view;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/20.
 */

public interface DeleteView {
    void onFailure(String msg);
    void onDeleteSuccess(String msg);
    void onDeleteFailure(String msg);
}
