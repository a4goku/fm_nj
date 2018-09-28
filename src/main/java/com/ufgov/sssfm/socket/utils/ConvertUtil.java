package com.ufgov.sssfm.socket.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;

public class ConvertUtil {

    //private static Logger log = Logger.getLogger(ConvertUtil.class);

    public static <T> List<T> rsToBeans(ResultSet resultSet, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<T> beans = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T bean = clazz.newInstance();
                for (Field field : fields) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    //log.debug(fieldName + "=" + value);
                    field.set(bean, value);
                }
                beans.add(bean);
            }
        } catch (SecurityException e) {
            //log.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            //log.error(e.getMessage(), e);
        } catch (SQLException e) {
            //log.error(e.getMessage(), e);
        } catch (InstantiationException e) {
            //log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            //log.error(e.getMessage(), e);
        }
        return beans;
    }

    public static List<Map<String, String>> rsToMaps(ResultSet resultSet)
            throws SQLException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<String> columnList = new ArrayList<String>();
        int count = resultSet.getMetaData().getColumnCount();
        for (int i = 0; i < count; i++) {
            columnList.add(resultSet.getMetaData().getColumnName(i + 1)
                    .toLowerCase());
        }
        while (resultSet.next()) {
            Map<String, String> map = new HashMap<String, String>();
            for (String column : columnList) {
                if (resultSet.getObject(column) == null) {
                    map.put(column, "");
                } else {
                    map.put(column, resultSet.getObject(column).toString());
                }
            }
            list.add(map);
        }
        return list;
    }
}
