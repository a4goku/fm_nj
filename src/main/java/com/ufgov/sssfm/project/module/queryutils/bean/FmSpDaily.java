package com.ufgov.sssfm.project.module.queryutils.bean;

public class FmSpDaily {
    private String id;

    private String spBusiness;

    private String czFs;

    private String czPeople;

    private String czTime;

    private Integer czNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSpBusiness() {
        return spBusiness;
    }

    public void setSpBusiness(String spBusiness) {
        this.spBusiness = spBusiness == null ? null : spBusiness.trim();
    }

    public String getCzFs() {
        return czFs;
    }

    public void setCzFs(String czFs) {
        this.czFs = czFs == null ? null : czFs.trim();
    }

    public String getCzPeople() {
        return czPeople;
    }

    public void setCzPeople(String czPeople) {
        this.czPeople = czPeople == null ? null : czPeople.trim();
    }

    public String getCzTime() {
        return czTime;
    }

    public void setCzTime(String czTime) {
        this.czTime = czTime == null ? null : czTime.trim();
    }

    public Integer getCzNum() {
        return czNum;
    }

    public void setCzNum(Integer czNum) {
        this.czNum = czNum;
    }
}