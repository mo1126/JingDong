package com.mo.jingdong.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.DeleteBean;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/20.
 */

public class DeleteModel {

    public void Delete(String uid,String pid){

        Map<String, String> params=new HashMap<>();
        params.put("uid",uid);
        params.put("pid",pid);
        NetRequest.Call(API.DELETE, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                onDelete.onFailure(call, e);
            }
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    DeleteBean deleteBean = gson.fromJson(string, DeleteBean.class);
                    if("0".equals(deleteBean)){
                        onDelete.onDeleteSuccess(deleteBean.msg);
                    }else{
                        onDelete.onDeleteFailure(deleteBean.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void setOnDelete(DeleteModel.onDelete onDelete) {
        this.onDelete = onDelete;
    }

    private onDelete onDelete;
    public interface onDelete{
        void onFailure(Call call, IOException e);
        void onDeleteSuccess(String msg);
        void onDeleteFailure(String msg);
    }
}
