package com.mo.jingdong.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.mo.jingdong.R;
import com.mo.jingdong.XiangqingActivity;
import com.mo.jingdong.entity.Xbanners;
import com.mo.jingdong.myAdapter.SyFenleiAdapter;
import com.mo.jingdong.myAdapter.MiaoShaAdapter;
import com.mo.jingdong.myAdapter.RecyclerAdapter;
import com.mo.jingdong.presenter.MainPresenter;
import com.mo.jingdong.view.MainView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mo on 2017/10/8.
 */

public class ShouyeFragment extends Fragment implements MainView {

    private View view;
    private RecyclerView recyclerView;
    private int mDistanceY;
    private Toolbar head_search_rr;
    private SuperSwipeRefreshLayout refreshLayout;
    private List<String> mdatas;
    private XBanner banner;
    private View inflate;
    private MainPresenter presenter;
    private View head2;
    private View miaosha;
    private ViewPager vp;
    private RecyclerView rc_miaosha;
    private List<Xbanners.TuijianBean.ListBean> list;
    private RecyclerAdapter mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sy, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initView();
        initData();
        jianting();

    }

    private void initData() {
        presenter = new MainPresenter(getContext(),this);
        presenter.getdata();
    }

    private void addheader() {
        List<Fragment> fragments=new ArrayList<>();
        Fenlei1 fenlei1 = new Fenlei1();
        Fenlei2 fenlei2 = new Fenlei2();
        fragments.add(fenlei1);
        fragments.add(fenlei2);
        SyFenleiAdapter myadapter=new SyFenleiAdapter(getActivity().getSupportFragmentManager(),fragments);
        vp.setAdapter(myadapter);

        mAdapter = new RecyclerAdapter(getContext(), R.layout.item_list, list);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(inflate);
        mHeaderAndFooterWrapper.addHeaderView(head2);
        mHeaderAndFooterWrapper.addHeaderView(miaosha);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        recyclerView.setAdapter(mHeaderAndFooterWrapper);

    }

    /**
     *标题栏滑动变化效果
     */
    private void jianting() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                int toolbarHeight = head_search_rr.getBottom();

                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    head_search_rr.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else {
                    //将标题栏的颜色设置为完全不透明状态
                    head_search_rr.setBackgroundResource(R.color.white);
                    getActivity().getWindow().setStatusBarColor(Color.BLACK);
                }
            }
        });

        refreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 500);
            }

            @Override
            public void onPullDistance(int distance) {
                if (distance > 0) {
                    head_search_rr.setVisibility(View.GONE);
                } else {
                    head_search_rr.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPullEnable(boolean enable) {
            }
        });
    }

    /**
     *初始化
     */
    private void initView() {
        head_search_rr =  view.findViewById(R.id.head_search_rr);
        ((AppCompatActivity)getActivity()).setSupportActionBar(head_search_rr);

        refreshLayout =  view.findViewById(R.id.swipe_container);
        recyclerView =  view.findViewById(R.id.recycler_view);

        inflate = LayoutInflater.from(getContext()).inflate(R.layout.head, null);
        banner =  inflate.findViewById(R.id.xbanner);
        head2 = LayoutInflater.from(getContext()).inflate(R.layout.head2, null);
        vp = head2.findViewById(R.id.vp);
        miaosha = LayoutInflater.from(getContext()).inflate(R.layout.miaosha, null);
        rc_miaosha = miaosha.findViewById(R.id.miaosha);
        RecyclerView.LayoutManager mLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void setXbanner(final List<Xbanners.DataBean> data) {
        System.out.println(data.get(0));
        if(getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 设置XBanner的页面切换特效
                    banner.setPageTransformer(Transformer.Default);
                    // 设置XBanner页面切换的时间，即动画时长
                    banner.setPageChangeDuration(1000);

                    banner.setData(data, null);
                    banner.setmAdapter(new XBanner.XBannerAdapter(){
                        @Override
                        public void loadBanner(XBanner banner, View view, int position) {
                            Glide.with(getContext()).load(data.get(position).icon).into((ImageView) view);
                        }
                    });
                    banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                        @Override
                        public void onItemClick(XBanner banner, int position) {
                            Intent intent = new Intent(getContext(), XiangqingActivity.class);
                            intent.putExtra("url",data.get(position).url);
                            startActivity(intent);
                        }
                    });
                }
            });
        }



    }

    @Override
    public void setMiaosha(final Xbanners.MiaoshaBean miaosha) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager lm=new LinearLayoutManager(getContext());
                lm.setOrientation(LinearLayoutManager.HORIZONTAL);
                rc_miaosha.setLayoutManager(lm);
                MiaoShaAdapter adapter=new MiaoShaAdapter(getContext(),miaosha);
                rc_miaosha.setAdapter(adapter);


            }
        });

    }

    @Override
    public void setTuijian(final Xbanners.TuijianBean tuijian) {
        list = tuijian.list;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addheader();
            }
        });

    }


}
