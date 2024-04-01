package com.example.smartguangzhou.pojo;

public class PojoMap {

    public String msg;
    public Integer code;
    public Data data;

    public static class Data {
        public Object searchValue;
        public Object createBy;
        public String createTime;
        public Object updateBy;
        public String updateTime;
        public Object remark;
        public Params params;
        public Integer id;
        public String name;
        public Integer code;
        public String imgUrl;

        public static class Params {

        }
    }
}
