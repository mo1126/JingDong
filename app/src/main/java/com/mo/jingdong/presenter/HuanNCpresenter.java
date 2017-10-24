package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.model.GaiNichengModel;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;
import com.mo.jingdong.view.HuanNCView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/13.
 */

public class HuanNCpresenter implements GaiNichengModel.gaiNicheng{
    private Context context;
    private HuanNCView huanNCView;
    private GaiNichengModel gaiNichengModel;

    public HuanNCpresenter(Context context, HuanNCView huanNCView) {
        this.context = context;
        this.huanNCView = huanNCView;
        gaiNichengModel=new GaiNichengModel();
        gaiNichengModel.setGaiNicheng(this);
    }

    public void updata(String uid,String nickname){
        gaiNichengModel.XiugaiReuest(API.GAINICHENG,uid,nickname);
    }

    @Override
    public void onFailure(Call call, IOException e) {
    huanNCView.onFailure(call,e);
    }

    @Override
    public void onXiuGaiSuccess(String msg) {
        huanNCView.onXiuGaiSuccess(msg);
    }

    @Override
    public void onXiuGaiFailure(String msg) {
        huanNCView.onXiuGaiFailure(msg);
    }
}
