package com.mo.jingdong.entity;

import java.util.List;

/**
 * Created by Mo on 2017/10/22.
 */

public class GetOrderBean {


    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2017-10-20T11:04:28","orderid":114,"price":38394,"status":0,"uid":98},{"createtime":"2017-10-21T16:20:30","orderid":512,"price":99,"status":0,"uid":98},{"createtime":"2017-10-22T12:38:13","orderid":750,"price":13332,"status":0,"uid":98}]
     * page : 1
     */

    public String msg;
    public String code;
    public String page;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * createtime : 2017-10-20T11:04:28
         * orderid : 114
         * price : 38394.0
         * status : 0
         * uid : 98
         */

        public String createtime;
        public int orderid;
        public double price;
        public int status;
        public int uid;
    }
}
