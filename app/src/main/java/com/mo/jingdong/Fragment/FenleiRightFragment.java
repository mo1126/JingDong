package com.mo.jingdong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.mo.jingdong.R;
import com.mo.jingdong.entity.ZiFenlei;
import com.mo.jingdong.myAdapter.ZiFenleiAdapter;
import com.mo.jingdong.presenter.ZifenleiPresenter;
import com.mo.jingdong.view.ZiFenleiView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/11.
 */

public class FenleiRightFragment extends Fragment implements ZiFenleiView{
    private int cid;
    private int p;
    private View inflate;
    private ImageView top;
    private RecyclerView fenlei_right_rc;
    private ZifenleiPresenter zifenleiPresenter;
    private List<Integer> imgs;
    private LinearLayoutManager lm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.fenlei_right, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initData() {
        imgs = new ArrayList<>();
        imgs.add(R.drawable.chaoshi);
        imgs.add(R.drawable.quanqiugou);
        imgs.add(R.drawable.shouji);
        imgs.add(R.drawable.nanzhuang);
        imgs.add(R.drawable.nvzhuang);


        zifenleiPresenter = new ZifenleiPresenter(this,getContext());
        zifenleiPresenter.getData(cid);

    }

    private void initView() {
        fenlei_right_rc = inflate.findViewById(R.id.fenlei_right_rc);
        View view= LayoutInflater.from(getContext()).inflate(R.layout.fenlei_right_top,null);
        top = view.findViewById(R.id.fenlei_top);

        lm = new LinearLayoutManager(getContext());
        fenlei_right_rc.setLayoutManager(lm);

        RecyclerViewHeader viewHeader=new RecyclerViewHeader(getContext());
        viewHeader.attachTo(fenlei_right_rc);
        viewHeader.addView(view);
    }

    public void getPosition(int position,int p1){
        cid=position;
        p=p1;
    }

    @Override
    public void onFenleiSuccess(final List<ZiFenlei.DataBean> data) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(p<5){
                       top.setImageResource(imgs.get(p));
                    }
                    ZiFenleiAdapter adapter=new ZiFenleiAdapter(getContext(),data);
                    fenlei_right_rc.setAdapter(adapter);
                }
            });
        }


    }

    @Override
    public void onFenleiFailure(final String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "网络请求有误", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
