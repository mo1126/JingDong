package com.mo.jingdong.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mo.jingdong.R;
import com.mo.jingdong.entity.ShangpinBean;
import com.mo.jingdong.presenter.ShangpinPresenter;
import com.mo.jingdong.view.ShangpinView;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mo on 2017/10/17.
 */

public class ShangpinFragment extends Fragment implements ShangpinView{

    private int pid;
    private View inflate;
    private XBanner banner;
    private ShangpinPresenter presenter;
    private TextView sp_title;
    private TextView sp_miaoshu;
    private TextView sp_price;

    public void getPid(int pid){
        this.pid=pid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.shangpin, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(getContext(), "点击了第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        presenter = new ShangpinPresenter(this,getContext());
        presenter.getXiangqing(String.valueOf(pid));
    }

    private void initView() {
        banner = inflate.findViewById(R.id.spbanner);
        sp_title = inflate.findViewById(R.id.sp_title);
        sp_miaoshu = inflate.findViewById(R.id.sp_miaoshu);
        sp_price = inflate.findViewById(R.id.sp_price);

     }

    @Override
    public void onFailure(final String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onXiangqingSuccess(final ShangpinBean shangpinBean) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setbanner(shangpinBean);
                    sp_title.setText(shangpinBean.data.title);
                    sp_miaoshu.setText(shangpinBean.data.subhead);
                    sp_price.setText("¥ "+shangpinBean.data.price);
                    Toast.makeText(getContext(), "获取详情成功"+shangpinBean.data.title, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setbanner(ShangpinBean shangpinBean) {
        final String images = shangpinBean.data.images;
        String[] split = images.split("\\|");
        final List<String> img=new ArrayList<String>();
        for (int i = 0; i < split.length; i++) {
            img.add(split[i]);
        }
        banner.setData(img,null);
        // XBanner适配数据
        banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(getContext()).load(img.get(position)).into((ImageView) view);
            }
        });
    }

    @Override
    public void onXiangqingFailure(final String msg) {
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
