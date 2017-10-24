package com.mo.jingdong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.myAdapter.GridViewAdapter;
import com.mo.jingdong.presenter.ShouyeFenleiPresenter;
import com.mo.jingdong.view.SyFenlei1View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mo on 2017/10/9.
 */

public class Fenlei2 extends Fragment implements SyFenlei1View {
    private View view;
    private RecyclerView rc_fenlei1;
    private ShouyeFenleiPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fenlei1, null);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    private void initData() {
        presenter = new ShouyeFenleiPresenter(this,getContext());
        presenter.getdata();

    }

    @Override
    public void setFenlei(final List<ShouyeFenlei.DataBean> data) {
        final List<ShouyeFenlei.DataBean> list=new ArrayList<>();
        for (int i = 10; i < data.size(); i++) {
            list.add(data.get(i));
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                rc_fenlei1 = view.findViewById(R.id.rc_fenlei1);
                GridLayoutManager gm=new GridLayoutManager(getContext(),5);
                rc_fenlei1.setLayoutManager(gm);
                rc_fenlei1.setAdapter(new GridViewAdapter(getContext(),list));
            }
        });
    }
}
