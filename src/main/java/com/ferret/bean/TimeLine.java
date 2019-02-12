package com.ferret.bean;

import java.util.Date;

public class TimeLine {
    private Integer id;

    private Integer globfeatureid;

    private Date timepoint;

    private String spare1;

    private Integer spare2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGlobfeatureid() {
        return globfeatureid;
    }

    public void setGlobfeatureid(Integer globfeatureid) {
        this.globfeatureid = globfeatureid;
    }

    public Date getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(Date timepoint) {
        this.timepoint = timepoint;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }

    public Integer getSpare2() {
        return spare2;
    }

    public void setSpare2(Integer spare2) {
        this.spare2 = spare2;
    }
}