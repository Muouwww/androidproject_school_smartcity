package com.example.smartguangzhou.pojo;

public class PojoNewsDetail {
    private String msg;
    private int code;
    private DataPojo data;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }public void setCode(int code) {
        this.code = code;
    }
    public DataPojo getData() {
        return data;
    }
    public void setData(DataPojo data) {
        this.data = data;
    }
    public static class DataPojo {
        private Object searchValue;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private Object remark;
        private ParamsPojo params;
        private int id;
        private String appType;
        private String cover;
        private String title;
        private Object subTitle;
        private String content;
        private String status;
        private String publishDate;
        private Object tags;
        private int commentNum;
        private int likeNum;
        private int readNum;
        private String type;
        private String top;
        private String hot;
        public Object getSearchValue() {
            return searchValue;
        }
        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }
        public String getCreateBy() {
            return createBy;
        }
        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }
        public String getCreateTime() {
            return createTime;
        }
        public void setCreateTime(String createTime) {
            this.createTime = createTime;}
        public String getUpdateBy() {
            return updateBy;
        }
        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }
        public String getUpdateTime() {
            return updateTime;
        }
        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
        public Object getRemark() {
            return remark;
        }
        public void setRemark(Object remark) {
            this.remark = remark;
        }
        public ParamsPojo getParams() {
            return params;
        }
        public void setParams(ParamsPojo params) {
            this.params = params;
        }
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getAppType() {
            return appType;
        }
        public void setAppType(String appType) {
            this.appType = appType;
        }
        public String getCover() {
            return cover;
        }
        public void setCover(String cover) {
            this.cover = cover;
        }public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public Object getSubTitle() {
            return subTitle;
        }
        public void setSubTitle(Object subTitle) {
            this.subTitle = subTitle;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getPublishDate() {
            return publishDate;
        }
        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }
        public Object getTags() {
            return tags;
        }
        public void setTags(Object tags) {
            this.tags = tags;
        }
        public int getCommentNum() {
            return commentNum;
        }
        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }
        public int getLikeNum() {
            return likeNum;
        }
        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }
        public int getReadNum() {
            return readNum;
        }
        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getTop() {
            return top;
        }
        public void setTop(String top) {
            this.top = top;
        }
        public String getHot() {
            return hot;
        }
        public void setHot(String hot) {
            this.hot = hot;
        }
        public static class ParamsPojo {
        }
    }
}
