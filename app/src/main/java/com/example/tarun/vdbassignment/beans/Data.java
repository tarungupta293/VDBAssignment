package com.example.tarun.vdbassignment.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tarun on 1/28/2019.
 */

public class Data {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String desc;
    @SerializedName("language")
    @Expose
    private String lanuage;
    @SerializedName("open_issues_count")
    @Expose
    private String openIssuesCount;
    @SerializedName("watchers")
    @Expose
    private String watchers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLanuage() {
        return lanuage;
    }

    public void setLanuage(String lanuage) {
        this.lanuage = lanuage;
    }

    public String getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setOpenIssuesCount(String openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    public String getWatchers() {
        return watchers;
    }

    public void setWatchers(String watchers) {
        this.watchers = watchers;
    }
}
