package com.mo.jingdong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mo.jingdong.Fragment.ShangpinFragment;
import com.mo.jingdong.Fragment.XiangqingFragment;
import com.mo.jingdong.comment.API;
import com.mo.jingdong.myAdapter.VpAdapter;
import com.mo.jingdong.utils.CallBack;
import com.mo.jingdong.utils.NetRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class XiangqingActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp;
    private TabLayout tab;
    private LinearLayout shopcar;
    private Button joinshopcar;
    private ShangpinFragment shangpinFragment;
    private XiangqingFragment xiangqingFragment;
    private int pid;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        initView();
        initData();
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("uid", MODE_PRIVATE);
        uid = sp.getInt("uid", 0);
        Intent intent = getIntent();
        pid = intent.getIntExtra("pid", 0);
        String url = intent.getStringExtra("url");
        List<Fragment> list=new ArrayList<>();
        shangpinFragment = new ShangpinFragment();
        shangpinFragment.getPid(pid);

        xiangqingFragment = new XiangqingFragment();
        xiangqingFragment.getUrl(url);
        list.add(shangpinFragment);
        list.add( xiangqingFragment);
        list.add(new Fragment());
        VpAdapter myadapter=new VpAdapter(getSupportFragmentManager(),this,list);
        vp.setAdapter(myadapter);

    }

    private void initView() {
        ImageView xq_back= (ImageView) findViewById(R.id.xq_back);
        ImageView xq_share= (ImageView) findViewById(R.id.xq_share);
        ImageView xq_gengduo= (ImageView) findViewById(R.id.xq_gengduo);
        vp = (ViewPager) findViewById(R.id.xq_vp);
        tab = (TabLayout) findViewById(R.id.tablayout);
        LinearLayout kefu= (LinearLayout) findViewById(R.id.kefu);
        LinearLayout dianpu= (LinearLayout) findViewById(R.id.dianpu);
        LinearLayout guanzhu= (LinearLayout) findViewById(R.id.guanzhu);
        shopcar = (LinearLayout) findViewById(R.id.shapcar);
        joinshopcar = (Button) findViewById(R.id.joinshapcar);

        joinshopcar.setOnClickListener(this);
        shopcar.setOnClickListener(this);
        xq_back.setOnClickListener(this);
        tab.setupWithViewPager(vp);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.joinshapcar:
                Map<String, String> params=new HashMap<>();
                params.put("uid", String.valueOf(uid));
                params.put("pid", String.valueOf(pid));
                NetRequest.Call(API.JOINCAR, params, new CallBack() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if(this!=null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(XiangqingActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(this!=null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(XiangqingActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.xq_back:
                finish();
                break;
            case R.id.shapcar:break;

        }
    }
}
