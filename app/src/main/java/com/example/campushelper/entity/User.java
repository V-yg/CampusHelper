package com.example.campushelper.entity;

/**
 * Created by 一钢 on 2018/9/28.
 */

public class User {

    private String account;
    private String password;
    private int id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
