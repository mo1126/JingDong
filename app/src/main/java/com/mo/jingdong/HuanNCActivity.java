package com.mo.jingdong;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.presenter.HuanNCpresenter;
import com.mo.jingdong.view.HuanNCView;

import java.io.IOException;

import okhttp3.Call;

public class HuanNCActivity extends AppCompatActivity implements HuanNCView{

    private EditText et_nicheng;
    private HuanNCpresenter presenter;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_nc);
        initView();
        initData();
    }

    private void initData() {
        presenter = new HuanNCpresenter(this,this);
        SharedPreferences sharedPreferences = getSharedPreferences("uid", MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid", 0);
    }

    private void initView() {
        et_nicheng = (EditText) findViewById(R.id.et_nickname);
    }

    public void gainicheng(View view){
        presenter.updata(uid+"",et_nicheng.getText().toString());
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HuanNCActivity.this, "网络有误,请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onXiuGaiSuccess(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HuanNCActivity.this, msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    @Override
    public void onXiuGaiFailure(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HuanNCActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
