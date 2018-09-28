//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.analysis.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("out:business")
public class BusinessDomain {
    @XStreamImplicit(
        itemFieldName = "result"
    )
    private List<Row> results;
    @XStreamImplicit(
        itemFieldName = "resultset"
    )
    private List<ResultSet> resultSets = new ArrayList();
    private String faultCode;
    private String faultString;
    private String isOK;

    public BusinessDomain() {
    }

    public List<Row> getResults() {
        return this.results;
    }

    public void setResults(List<Row> results) {
        this.results = results;
    }

    public List<ResultSet> getResultSets() {
        return this.resultSets;
    }

    public void setResultSets(List<ResultSet> resultSets) {
        this.resultSets = resultSets;
    }

    public String getIsOK() {
        return this.isOK;
    }

    public void setIsOK(String isOK) {
        this.isOK = isOK;
    }

    public String getFaultCode() {
        return this.faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return this.faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
}
