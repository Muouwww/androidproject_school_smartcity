package com.example.smartguangzhou.pojo;

public class PojoUser {
    private String msg;
    private int code;
    private UserPojo user;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public UserPojo getUser() {
        return user;
    }
    public void setUser(UserPojo user) {
        this.user = user;
    }
    public static class UserPojo {
        private int userId;
        private String userName;
        private String nickName;
        private String email;
        private String phonenumber;
        private String sex;
        private String avatar;
        private String idCard;
        private double balance;
        private int score;
        public int getUserId() {
            return userId;
        }
        public void setUserId(int userId) {
            this.userId = userId;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getNickName() {
            return nickName;
        }
        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPhonenumber() {
            return phonenumber;
        }
        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }
        public String getSex() {
            return sex;
        }
        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getAvatar() {
            return avatar;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
        public String getIdCard() {
            return idCard;
        }
        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }
        public double getBalance() {
            return balance;
        }
        public void setBalance(double balance) {
            this.balance = balance;
        }
        public int getScore() {
            return score;
        }
        public void setScore(int score) {
            this.score = score;
        }
    }
}
