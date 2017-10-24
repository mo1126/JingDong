package com.mo.jingdong.entity;

/**
 * Created by Mo on 2017/10/12.
 */

public class LoginBean {


    /**
     * msg : 登录成功
     * code : 0
     * data : {"age":null,"createtime":"2017-10-08T00:00:00","gender":0,"icon":null,"mobile":"15011217423","money":0,"nickname":null,"password":"971121","uid":98,"username":"15011217423"}
     */

    public String msg;
    public String code;
    public DataBean data;

    public static class DataBean {
        /**
         * age : null
         * createtime : 2017-10-08T00:00:00
         * gender : 0
         * icon : null
         * mobile : 15011217423
         * money : 0
         * nickname : null
         * password : 971121
         * uid : 98
         * username : 15011217423
         */

        public Object age;
        public String createtime;
        public int gender;
        public Object icon;
        public String mobile;
        public int money;
        public Object nickname;
        public String password;
        public int uid;
        public String username;
    }
}
