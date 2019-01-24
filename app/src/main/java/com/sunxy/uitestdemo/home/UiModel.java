package com.sunxy.uitestdemo.home;

import android.app.Activity;

import java.io.Serializable;

/**
 * SunXiaoYu on 2019/1/23.
 * mail: sunxiaoyu@hexinpass.com
 */
public class UiModel<T extends Activity> implements Serializable {

    private String title;
    private Class<T> clazz;

    public UiModel() {
    }

    public UiModel(String title, Class<T> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
