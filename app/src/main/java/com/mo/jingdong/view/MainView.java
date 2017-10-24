package com.mo.jingdong.view;

import com.mo.jingdong.entity.Xbanners;

import java.util.List;

/**
 * Created by Mo on 2017/10/8.
 */

public interface MainView {
    void setXbanner(List<Xbanners.DataBean> data);
    void setMiaosha(Xbanners.MiaoshaBean miaosha);
    void setTuijian(Xbanners.TuijianBean tuijian);

}
