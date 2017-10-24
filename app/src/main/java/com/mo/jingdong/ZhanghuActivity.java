package com.mo.jingdong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mo.jingdong.entity.GetUserInfoBean;
import com.mo.jingdong.presenter.GetInfoPresenter;
import com.mo.jingdong.view.GetInfoView;

import java.io.IOException;

import okhttp3.Call;

public class ZhanghuActivity extends AppCompatActivity implements GetInfoView{

    private ImageView iv;
    private TextView nickname;
    private RelativeLayout user_rl;
    private GetInfoPresenter presenter;
    private TextView username;
    private Button tuichu;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhanghu);
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("uid", MODE_PRIVATE);
        uid = sp.getInt("uid", 0);
        presenter = new GetInfoPresenter(this,this);
        presenter.getData(uid);
    }

    private void initData() {
        //presenter.getData(uid);
    }

    private void initView() {
        tuichu = (Button) findViewById(R.id.tuichu);
        iv = (ImageView) findViewById(R.id.img_zhanghu);
        nickname = (TextView) findViewById(R.id.tv_nickname);
        username = (TextView) findViewById(R.id.tv_username);
        user_rl = (RelativeLayout) findViewById(R.id.user_rl);
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences uid = getSharedPreferences("uid", MODE_PRIVATE);
                uid.edit().clear().commit();
                finish();
            }
        });
        user_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ZhanghuActivity.this,GerenActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(Call call, IOException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZhanghuActivity.this, "网络有误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onGetInfoSuccess(final GetUserInfoBean getUserInfoBean) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(getUserInfoBean.data.icon!=null){
                        Glide.with(ZhanghuActivity.this).load(getUserInfoBean.data.icon) .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
                    }
                    username.setText("用户名:"+getUserInfoBean.data.username);
                    if(getUserInfoBean.data.nickname!=null){
                        nickname.setText(getUserInfoBean.data.nickname.toString());
                    }else{
                        nickname.setText(getUserInfoBean.data.username);
                    }
                }
            });
        }

    }

    @Override
    public void onGetInfoFailure(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZhanghuActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
