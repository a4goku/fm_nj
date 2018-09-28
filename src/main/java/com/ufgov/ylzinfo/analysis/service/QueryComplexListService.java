//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.analysis.service;

import com.ylzinfo.analysis.domain.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryComplexListService extends EsbService {
    private static final String DEFAULT_RESULTSET_DATA_NAME = "retrieve";
    private List queryList;
    Map<String, List> mdatasMap = new HashMap();

    public QueryComplexListService() {
    }

    public List<Row> getQueryList() {
        return this.queryList;
    }

    public void init() {
        this.queryList = (List)this.getMdatasMap().get("retrieve");
    }

    public Map<String, List> getMdatasMap() {
        return this.mdatasMap;
    }

    public void setMdatasMap(Map<String, List> mdatasMap) {
        this.mdatasMap = mdatasMap;
    }
}
