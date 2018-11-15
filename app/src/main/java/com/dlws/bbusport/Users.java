package com.dlws.bbusport;

public class Users {
    private String id;
    private String pwd;
    private String name;
    private String sex;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
