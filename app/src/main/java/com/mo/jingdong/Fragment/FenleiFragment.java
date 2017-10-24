package com.mo.jingdong.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShouyeFenlei;
import com.mo.jingdong.myAdapter.FenleiLeftAdapter;
import com.mo.jingdong.presenter.FenleiPresenter;
import com.mo.jingdong.view.FenleiView;

import java.util.List;

/**
 * Created by Mo on 2017/10/10.
 */

public class FenleiFragment extends Fragment implements FenleiView{

    private View view;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_fl, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        FenleiRightFragment fenleiRightFragment = new FenleiRightFragment();
        fenleiRightFragment.getPosition(1,0);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fenlei_right_fl,fenleiRightFragment).commit();
    }

    private void initData() {
        FenleiPresenter presenter=new FenleiPresenter(this,getContext());
        presenter.getdata();
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.fenlei_rc);
    }

    @Override
    public void fenleiSuccess(final List<ShouyeFenlei.DataBean> data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager lm=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(lm);
                final FenleiLeftAdapter leftAdapter=new FenleiLeftAdapter(getContext(),data);
                recyclerView.setAdapter(leftAdapter);
                leftAdapter.setOnItemClick(new FenleiLeftAdapter.onItemClick() {
                    @Override
                    public void onItemClick(int position,View view) {
                        Toast.makeText(getContext(), data.get(position).name, Toast.LENGTH_SHORT).show();
                        leftAdapter.getid(position);
                        FenleiRightFragment fenleiRightFragment = new FenleiRightFragment();
                        fenleiRightFragment.getPosition(data.get(position).cid,position);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fenlei_right_fl,fenleiRightFragment).commit();
                    }
                });
            }
        });
    }

    @Override
    public void fenleiFailure(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onFailure(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
