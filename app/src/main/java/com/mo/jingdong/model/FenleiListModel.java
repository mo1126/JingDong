package com.mo.jingdong.model;

import android.content.Context;

import com.google.gson.Gson;
import com.mo.jingdong.entity.FenLeiListBean;
import com.mo.jingdong.entity.SousuoBean;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mo on 2017/10/13.
 */

public class FenleiListModel {
    public void FenleiListRequest(String url,String pscid,String page,String sort){
        Map<String, String> params=new HashMap<>();
        params.put("pscid",pscid);
        if(page!=null){
            params.put("page",page);
        }
        if(sort!=null){
            params.put("sort",sort);
        }
        NetRequest.Call(url, params, new CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                fenleiList.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    FenLeiListBean fenLeiListBean = gson.fromJson(string, FenLeiListBean.class);
                    if("0".equals(fenLeiListBean.code)){
                        fenleiList.onFenleiListSuccess(fenLeiListBean);
                    }else{
                        fenleiList.onFenleiListFailure(fenLeiListBean.msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private getSousuo getSousuo;

    public void setGetSousuo(getSousuo getSousuo) {
        this.getSousuo = getSousuo;
    }

    public interface getSousuo{
        void onFailure(Call call, IOException e);
        void onGetSousuoSuccess(SousuoBean getUserInfoBean);
        void onGetSousuoFailure(String msg);
    }
    public void getSousuo(String url, String keywords,String page,String sort){
        Map<String, String> params=new HashMap<>();
        params.put("keywords",keywords);
        if(page!=null){
            params.put("page",page);
        }
        if(sort!=null){
            params.put("sort",sort);
        }
        NetRequest.Call(url, params, new  CallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                getSousuo.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String string = response.body().string();
                    if(string!=null){
                        Gson gson=new Gson();
                        SousuoBean getsousuoBean = gson.fromJson(string, SousuoBean.class);
                        if("0".equals(getsousuoBean.code)){
                            getSousuo.onGetSousuoSuccess(getsousuoBean);
                        }else{
                            getSousuo.onGetSousuoFailure(getsousuoBean.msg);
                        }
                    }else{
                        return;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private FenleiList fenleiList;

    public void setFenleiList(FenleiList fenleiList) {
        this.fenleiList = fenleiList;
    }

    public interface FenleiList{
        void onFailure(Call call, IOException e);
        void onFenleiListSuccess(FenLeiListBean fenLeiListBean);
        void onFenleiListFailure(String msg);
    }

}
