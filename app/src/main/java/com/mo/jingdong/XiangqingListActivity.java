package com.mo.jingdong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.jingdong.entity.FenLeiListBean;
import com.mo.jingdong.entity.SousuoBean;
import com.mo.jingdong.myAdapter.FenleiListAdapter;
import com.mo.jingdong.presenter.FenLeiListPresenter;
import com.mo.jingdong.view.FenleiListView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class XiangqingListActivity extends AppCompatActivity implements View.OnClickListener,FenleiListView {

    private RecyclerView rc;
    private ImageView iv_leixing;
    private TextView px_zonghe;
    private TextView px_xiaoliang;
    private TextView px_jiage;
    private ImageView sousuo;
    private FenLeiListPresenter presenter;
    private String pcid;
    private Boolean geshi;
    private FenleiListAdapter myadapter;
    private int paixu;
    private int page;
    private EditText et_sousuo;
    private boolean ssflag=false;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_xiangqing_list);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        pcid = intent.getStringExtra("pcid");
        paixu=0;
        page=1;
        geshi = true;

        presenter = new FenLeiListPresenter(this,this);
        presenter.getData(pcid,null, null);
    }

    private void initView() {
        rc = (RecyclerView) findViewById(R.id.fenlei_list_rc);
        iv_leixing = (ImageView) findViewById(R.id.iv_leixing);
        sousuo = (ImageView) findViewById(R.id.ss);
        et_sousuo = (EditText) findViewById(R.id.et_sousuo);
        px_zonghe = (TextView) findViewById(R.id.px_zonghe);
        px_xiaoliang = (TextView) findViewById(R.id.px_xiaoliang);
        px_jiage = (TextView) findViewById(R.id.px_jiage);
        back = (ImageView) findViewById(R.id.xqlist_back);
        back.setOnClickListener(this);
        sousuo.setOnClickListener(this);
        iv_leixing.setOnClickListener(this);
        px_zonghe.setOnClickListener(this);
        px_xiaoliang.setOnClickListener(this);
        px_jiage.setOnClickListener(this);
        px_zonghe.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_leixing:
                if(geshi){
                    iv_leixing.setImageResource(R.drawable.kind_grid);
                    geshi=false;
                    presenter.getData(pcid,String.valueOf(page), String.valueOf(paixu));
                }else{
                    iv_leixing.setImageResource(R.drawable.kind_liner);
                    geshi=true;
                    presenter.getData(pcid,String.valueOf(page), String.valueOf(paixu));
                }
                Toast.makeText(this, geshi+"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ss:
                myadapter=null;
                String guanjian = et_sousuo.getText().toString();
                if(guanjian!=null){
                    presenter.getSousuo(et_sousuo.getText().toString(),null,null);
                }else{
                    Toast.makeText(this, "关键词不能为空", Toast.LENGTH_SHORT).show();
                }
                ssflag = true;
                break;
            case R.id.px_zonghe:
                if(ssflag){
                    presenter.getSousuo(et_sousuo.getText().toString(), String.valueOf(1), String.valueOf(0));
                    // TODO: 2017/10/16 搜索排序
                }else{
                    myadapter=null;
                    paixu=0;
                    presenter.getData(pcid,String.valueOf(page), String.valueOf(paixu));
                }
                px_zonghe.setSelected(true);
                px_xiaoliang.setSelected(false);
                px_jiage.setSelected(false);
                break;
            case R.id.px_xiaoliang:
                if(ssflag){
                    presenter.getSousuo(et_sousuo.getText().toString(), String.valueOf(1), String.valueOf(1));
                }else{
                    myadapter=null;
                    paixu=1;
                    presenter.getData(pcid,String.valueOf(page), String.valueOf(paixu));

                }
                px_xiaoliang.setSelected(true);
                px_zonghe.setSelected(false);
                px_jiage.setSelected(false);
                break;
            case R.id.px_jiage:
                if(ssflag){
                    presenter.getSousuo(et_sousuo.getText().toString(), String.valueOf(1), String.valueOf(2));
                }else{
                    myadapter=null;
                    paixu=2;
                    presenter.getData(pcid,String.valueOf(page), String.valueOf(paixu));
                }
                px_jiage.setSelected(true);
                px_zonghe.setSelected(false);
                px_xiaoliang.setSelected(false);
                break;
            case R.id.xqlist_back:
                finish();
                break;
        }
    }

    @Override
    public void onFailure(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(XiangqingListActivity.this, msg ,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onFenleiListSuccess(final FenLeiListBean fenLeiListBean) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(geshi){
                        LinearLayoutManager lm=new LinearLayoutManager(XiangqingListActivity.this);
                        rc.setLayoutManager(lm);
                    }else{
                        GridLayoutManager gm=new GridLayoutManager(XiangqingListActivity.this,2);
                        rc.setLayoutManager(gm);
                    }

                    if(myadapter==null){
                        myadapter = new FenleiListAdapter(XiangqingListActivity.this,fenLeiListBean.data, geshi);
                    }else{
                        myadapter.setgeshi(geshi);
                        myadapter.notifyDataSetChanged();
                    }
                    rc.setAdapter(myadapter);
                    Toast.makeText(XiangqingListActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onFenleiListFailure(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(XiangqingListActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onGetSousuoSuccess(final SousuoBean getUserInfoBean) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(geshi){
                        LinearLayoutManager lm=new LinearLayoutManager(XiangqingListActivity.this);
                        rc.setLayoutManager(lm);
                    }else{
                        GridLayoutManager gm=new GridLayoutManager(XiangqingListActivity.this,2);
                        rc.setLayoutManager(gm);
                    }

                    if(myadapter==null){
                        myadapter = new FenleiListAdapter(getUserInfoBean.data, geshi,XiangqingListActivity.this);
                    }else{
                        myadapter.setgeshi(geshi);
                        myadapter.notifyDataSetChanged();
                    }
                    rc.setAdapter(myadapter);
                    Toast.makeText(XiangqingListActivity.this, "搜索成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onGetSousuoFailure(final String msg) {
        if(this!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(XiangqingListActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
