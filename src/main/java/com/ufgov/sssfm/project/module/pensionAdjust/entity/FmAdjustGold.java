package com.ufgov.sssfm.project.module.pensionAdjust.entity;

public class FmAdjustGold {
    private String id;

    private String year;

    private String quarter;

    private String sbjz;

    private String sbld;

    private String sbcz;

    private String sbjbr;

    private String czld;

    private String czzr;

    private String czjbr;

    private String bzdate;

    private String shdate;

    private String spStatus;

    private String sendStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    public String getSbjz() {
        return sbjz;
    }

    public void setSbjz(String sbjz) {
        this.sbjz = sbjz == null ? null : sbjz.trim();
    }

    public String getSbld() {
        return sbld;
    }

    public void setSbld(String sbld) {
        this.sbld = sbld == null ? null : sbld.trim();
    }

    public String getSbcz() {
        return sbcz;
    }

    public void setSbcz(String sbcz) {
        this.sbcz = sbcz == null ? null : sbcz.trim();
    }

    public String getSbjbr() {
        return sbjbr;
    }

    public void setSbjbr(String sbjbr) {
        this.sbjbr = sbjbr == null ? null : sbjbr.trim();
    }

    public String getCzld() {
        return czld;
    }

    public void setCzld(String czld) {
        this.czld = czld == null ? null : czld.trim();
    }

    public String getCzzr() {
        return czzr;
    }

    public void setCzzr(String czzr) {
        this.czzr = czzr == null ? null : czzr.trim();
    }

    public String getCzjbr() {
        return czjbr;
    }

    public void setCzjbr(String czjbr) {
        this.czjbr = czjbr == null ? null : czjbr.trim();
    }

    public String getBzdate() {
        return bzdate;
    }

    public void setBzdate(String bzdate) {
        this.bzdate = bzdate == null ? null : bzdate.trim();
    }

    public String getShdate() {
        return shdate;
    }

    public void setShdate(String shdate) {
        this.shdate = shdate == null ? null : shdate.trim();
    }

    public String getSpStatus() {
        return spStatus;
    }

    public void setSpStatus(String spStatus) {
        this.spStatus = spStatus == null ? null : spStatus.trim();
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }
}