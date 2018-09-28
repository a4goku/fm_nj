//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.analysis.service;

import com.ylzinfo.analysis.domain.BusinessDomain;
import com.ylzinfo.analysis.domain.FaultDomain;
import com.ylzinfo.analysis.domain.ResultSet;
import com.ylzinfo.analysis.domain.Row;
import com.ylzinfo.esb.bas.EsbException;
import com.ylzinfo.esb.util.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class EsbService {
    private static final String STRUCTS = "structs";
    public static final String SUCCESS_MARK = "SUCCESS";
    public static final String OK_MARK = "OK";
    List<Row> struct = null;
    Map<String, List<Row>> datasMap = new HashMap();
    Map<String, String> resultMap = null;
    String currentPage = "0";
    String totalPages = "0";
    String totalCounts = "0";
    String information = null;
    String faultCode = "";
    String faultString = "";
    boolean isOK = false;

    public boolean isOK() {
        return this.isOK;
    }

    public String getFaultString() {
        return this.faultString;
    }

    public String getFaultCode() {
        return this.faultCode;
    }

    public String getCurrentPage() {
        return this.currentPage;
    }

    public String getTotalPages() {
        return this.totalPages;
    }

    public String getTotalCounts() {
        return this.totalCounts;
    }

    public String getInformation() {
        return this.information;
    }

    public EsbService() {
    }

    public void beforeInit() {
    }

    public void doInit(BusinessDomain data) throws EsbException {
        this.beforeInit();
        List<ResultSet> resultSets = data.getResultSets();
        if (resultSets != null && resultSets.size() > 0) {
            Iterator var4 = resultSets.iterator();

            while(var4.hasNext()) {
                ResultSet resultSet = (ResultSet)var4.next();
                this.datasMap.put(resultSet.getName(), resultSet.getRows());
            }
        }

        List<Row> results = data.getResults();
        if (results != null) {
            this.resultMap = new HashMap();
            Iterator var5 = results.iterator();

            while(var5.hasNext()) {
                Row result = (Row)var5.next();
                this.resultMap.putAll(result);
                if (StringUtil.hasText((String)result.get("cpage"))) {
                    this.currentPage = (String)result.get("cpage");
                }

                if (StringUtil.hasText((String)result.get("pages"))) {
                    this.totalPages = (String)result.get("pages");
                }

                if (StringUtil.hasText((String)result.get("rowCount"))) {
                    this.totalCounts = (String)result.get("rowCount");
                }

                if (StringUtil.hasText((String)result.get("totalnum"))) {
                    this.totalCounts = (String)result.get("totalnum");
                }

                if (StringUtil.hasText((String)result.get("total"))) {
                    this.totalCounts = (String)result.get("total");
                }

                if (StringUtil.hasText((String)result.get("information"))) {
                    this.information = (String)result.get("information");
                }
            }
        }

        if (StringUtil.hasText(data.getFaultCode())) {
            this.faultCode = data.getFaultCode();
        }

        if (StringUtil.hasText(data.getFaultString())) {
            this.faultString = data.getFaultString();
        }

        if (StringUtil.hasText(data.getIsOK())) {
            this.isOK = "yes".equals(data.getIsOK());
        }

        this.init();
        this.afterInit();
    }

    public void doInit(FaultDomain data) throws EsbException {
        this.beforeInit();
        List<String> faultCodeList = data.getFaultCode();
        if (faultCodeList != null && faultCodeList.size() > 0) {
            this.faultCode = (String)faultCodeList.get(0);
        }

        List<String> fsList = data.getFaultString();
        if (fsList != null && fsList.size() > 0) {
            this.faultString = (String)fsList.get(0);
        }

        this.init();
        this.afterInit();
    }

    public abstract void init();

    public void afterInit() {
    }

    public List<Row> getStruct() {
        return this.struct;
    }

    public Map<String, List<Row>> getDatasMap() {
        return this.datasMap;
    }

    public Map<String, String> getResultMap() {
        return this.resultMap;
    }
}
