package com.mo.jingdong.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mo.jingdong.DingdanActivity;
import com.mo.jingdong.LoginActivity;
import com.mo.jingdong.R;
import com.mo.jingdong.ZhanghuActivity;
import com.mo.jingdong.entity.GetUserInfoBean;
import com.mo.jingdong.presenter.GetInfoPresenter;
import com.mo.jingdong.view.GetInfoView;

import java.io.IOException;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mo on 2017/10/11.
 */

public class WoDeFragment extends Fragment implements View.OnClickListener, GetInfoView {

    private View inflate;
    private LinearLayout login_ll;
    private int uid;
    private TextView name;
    private ImageView img_denglu;
    private GetInfoPresenter presenter;
    private LinearLayout dingdan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.wode, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }



    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getActivity().getSharedPreferences("uid", MODE_PRIVATE);
        uid = sp.getInt("uid", 0);
        if(uid==0){
            img_denglu.setImageResource(R.drawable.denglu);
            name.setText("登录/注册>");
           return;
        }else{
            presenter.getData(uid);
        }

    }

    private void initData() {
        presenter = new GetInfoPresenter(this,getContext());
    }

    private void initView() {
        login_ll = inflate.findViewById(R.id.rl_dl);
        name = inflate.findViewById(R.id.tv_user);
        img_denglu = inflate.findViewById(R.id.img_denglu);
        dingdan = inflate.findViewById(R.id.dingdan);
        login_ll.setOnClickListener(this);
        dingdan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_dl:
                if(uid!=0){
                    Intent intent=new Intent(getContext(), ZhanghuActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.dingdan:
                Intent intent=new Intent(getContext(), DingdanActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
            if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "网络有误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    @Override
    public void onGetInfoSuccess(final GetUserInfoBean getUserInfoBean) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(getUserInfoBean.data.nickname!=null){
                        name.setText(getUserInfoBean.data.nickname.toString());
                    }else{
                        name.setText(getUserInfoBean.data.username.toString());
                    }

                    if(getUserInfoBean.data.icon!=null){
                        Glide.with(getContext()).load(getUserInfoBean.data.icon).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE) .into(img_denglu);
                    }


                }
            });
        }
    }

    @Override
    public void onGetInfoFailure(String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}
