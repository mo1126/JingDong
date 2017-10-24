package com.mo.jingdong;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mo.jingdong.entity.LoginBean;
import com.mo.jingdong.presenter.LoginPresenter;
import com.mo.jingdong.view.LoginView;

import java.io.IOException;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private Button btn_login;
    private EditText pwd;
    private EditText name;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initData() {
        presenter = new LoginPresenter(this,this);
    }

    private void initView() {
        name = (EditText) findViewById(R.id.et_name);
        pwd = (EditText) findViewById(R.id.et_psw);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "网络请求有误", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public void onLoginSuccess(final LoginBean loginBean) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences uid = getSharedPreferences("uid", MODE_PRIVATE);
                    SharedPreferences.Editor edit = uid.edit();
                    edit.putInt("uid",loginBean.data.uid);
                    edit.commit();
                    finish();
                }
            });

        }

    }

    @Override
    public void onLoginFailure(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                presenter.getData(name.getText().toString(),pwd.getText().toString());
                break;
        }
    }
}
