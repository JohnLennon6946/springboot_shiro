package com.yusiyu.springbootshiro.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role() {
    }

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
