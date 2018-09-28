//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.analysis.domain;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class FaultString {
    @XStreamImplicit(
        itemFieldName = "error"
    )
    private List<Row> error;

    public FaultString() {
    }

    public List<Row> getError() {
        return this.error;
    }

    public void setError(List<Row> error) {
        this.error = error;
    }
}
