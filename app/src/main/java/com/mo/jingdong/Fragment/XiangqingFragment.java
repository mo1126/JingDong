package com.mo.jingdong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mo.jingdong.R;

/**
 * Created by Mo on 2017/10/17.
 */

public class XiangqingFragment  extends Fragment{

    private View view;
    private WebView wv;
    private String url;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.xiangqingfragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();


    }

    private void initData() {
        wv.loadUrl(url);
    }

    private void initView() {
        wv = view.findViewById(R.id.xq_wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
    }

    public void getUrl(String u) {
        this.url=u;
    }
}
