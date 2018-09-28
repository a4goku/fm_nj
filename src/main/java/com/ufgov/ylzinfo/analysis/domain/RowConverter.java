//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.analysis.domain;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import java.util.Iterator;
import java.util.Map.Entry;

public class RowConverter extends AbstractCollectionConverter {
    public RowConverter(Mapper mapper) {
        super(mapper);
    }

    public boolean canConvert(Class cls) {
        return cls.equals(Row.class);
    }

    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        Row map = (Row)obj;
        Iterator iterator = map.entrySet().iterator();

        while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            writer.addAttribute(entry.getKey().toString(), entry.getValue().toString());
        }

    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Row map = new Row();
        this.populateMap(reader, context, map);
        return map;
    }

    protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Row map) {
        Iterator iterator = reader.getAttributeNames();

        while(iterator.hasNext()) {
            Object key = iterator.next();
            String value = reader.getAttribute((String)key);
            map.put(key.toString(), value.toString());
        }

    }
}
