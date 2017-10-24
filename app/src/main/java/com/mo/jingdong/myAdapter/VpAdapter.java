package com.mo.jingdong.myAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mo on 2017/10/17.
 */

public class VpAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> list;

    public VpAdapter(FragmentManager fm,Context context,List<Fragment> list) {
        super(fm);
        this.context=context;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        List<String> name=new ArrayList<>();
        name.add("商品");
        name.add("详情");
        name.add("评价");
        return name.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
