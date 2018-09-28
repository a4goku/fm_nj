//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.analysis.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("soap:Fault")
public class FaultDomain {
    @XStreamImplicit(
        itemFieldName = "faultcode"
    )
    private List<String> faultCode;
    @XStreamImplicit(
        itemFieldName = "faultstring"
    )
    private List<String> faultString;

    public FaultDomain() {
    }

    public List<String> getFaultCode() {
        return this.faultCode;
    }

    public void setFaultCode(List<String> faultCode) {
        this.faultCode = faultCode;
    }

    public List<String> getFaultString() {
        return this.faultString;
    }

    public void setFaultString(List<String> faultString) {
        this.faultString = faultString;
    }
}
