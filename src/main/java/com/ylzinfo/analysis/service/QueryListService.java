//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.analysis.service;

import com.ylzinfo.analysis.domain.Row;

import java.util.List;

public class QueryListService extends EsbService {
    private static final String DEFAULT_RESULTSET_DATA_NAME = "retrieve";
    private List<Row> queryList;

    public QueryListService() {
    }

    public List<Row> getQueryList() {
        return this.queryList;
    }

    public void init() {
        this.queryList = (List)this.getDatasMap().get("retrieve");
    }
}
