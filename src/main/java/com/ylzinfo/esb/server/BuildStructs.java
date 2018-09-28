//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ylzinfo.esb.server;

import com.ylzinfo.esb.util.Structs;

import java.util.Iterator;
import java.util.List;

public class BuildStructs {
    public BuildStructs() {
    }

    public static String getStructsData(List<Structs> structsList) {
        StringBuffer StrStructs = new StringBuffer();
        String type = "Varchar2";
        Iterator var4 = structsList.iterator();

        while(var4.hasNext()) {
            Structs struct = (Structs)var4.next();
            type = struct.getType() == null ? type : struct.getType();
            StrStructs.append("<row ");
            StrStructs.append(" label=\"").append(struct.getLabel()).append("\"");
            StrStructs.append(" columnname=\"").append(struct.getColumnname()).append("\" typename=\"").append(type).append("\" />");
        }

        return StrStructs.toString();
    }
}
