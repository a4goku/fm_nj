//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.analysis.exception;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.ylzinfo.analysis.domain.Row;

@XStreamAlias("out:business")
public class EsbException extends YlzAppException {
    private static final long serialVersionUID = -5774398513446340510L;
    @XStreamAlias("result")
    private Row row;

    public Row getRow() {
        return this.row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public EsbException(String msg) {
        super(msg);
    }

    public EsbException(String msg, Throwable e) {
        super(msg, e);
    }

    public EsbException() {
    }

    public String getMessage() {
        return this.row == null ? super.getMessage() : (String)this.row.get("information");
    }
}
