package com.ferret.bean;

public class FeatureInfo {
    private Integer id;

    private String featurefile;

    private String property;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeaturefile() {
        return featurefile;
    }

    public void setFeaturefile(String featurefile) {
        this.featurefile = featurefile == null ? null : featurefile.trim();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }
}