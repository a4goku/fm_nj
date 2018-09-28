//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.esb.util;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"label", "columnname", "type", "datatype"}
)
@XmlRootElement(
    name = "structs"
)
public class Structs implements Serializable {
    private static final long serialVersionUID = 8908229973714087780L;
    @XmlElement(
        required = true
    )
    protected String label;
    @XmlElement(
        required = true
    )
    protected String columnname;
    protected String type;
    protected String datatype;

    public Structs() {
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String value) {
        this.label = value;
    }

    public String getColumnname() {
        return this.columnname;
    }

    public void setColumnname(String value) {
        this.columnname = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getDatatype() {
        return this.datatype;
    }

    public void setDatatype(String value) {
        this.datatype = value;
    }
}
