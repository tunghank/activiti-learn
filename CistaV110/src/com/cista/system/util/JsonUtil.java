package com.cista.system.util;

import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
   
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;  
  
/** 
 * 處理json的工具類，負責json數據轉換成java對象和java對象轉換成json 
 *  
 * @author  
 * @date 
 * @version 1.0 
 */  
public class JsonUtil {  
  
    /** 
     * 從一個JSON 對象字符格式中得到一個java對象 
     *  
     * @param jsonString 
     * @param pojoCalss 
     * @return 
     */  
    public static Object getObject4JsonString(String jsonString, Class pojoCalss) {  
        Object pojo;  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        pojo = JSONObject.toBean(jsonObject, pojoCalss);  
        return pojo;  
    }  
  
  
    /** 
     * 從json HASH表達式中獲取一個map，改map支持嵌套功能 
     *  
     * @param jsonString 
     * @return 
     */  
    public static Map getMap4Json(String jsonString) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Iterator keyIter = jsonObject.keys();  
        String key;  
        Object value;  
        Map valueMap = new HashMap();  
  
        while (keyIter.hasNext()) {  
            key = (String) keyIter.next();  
            value = jsonObject.get(key);  
            valueMap.put(key, value);  
        }  
  
        return valueMap;  
    }  
  
  
    /** 
     * 從json數組中得到相應java數組 
     *  
     * @param jsonString 
     * @return 
     */  
    public static Object[] getObjectArray4Json(String jsonString) {  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        return jsonArray.toArray();  
  
    }  
  
  
    /** 
     * 從json對象集合表達式中得到一個java對象列表 
     *  
     * @param jsonString 
     * @param pojoClass 
     * @return 
     */  
    public static List getList4Json(String jsonString, Class pojoClass) {  
  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        JSONObject jsonObject;  
        Object pojoValue;  
  
        List list = new ArrayList();  
        for (int i = 0; i < jsonArray.size(); i++) {  
  
            jsonObject = jsonArray.getJSONObject(i);  
            pojoValue = JSONObject.toBean(jsonObject, pojoClass);  
            list.add(pojoValue);  
  
        }  
        return list;  
  
    }  
  
  
    /** 
     * 從json數組中解析出java字符串數組 
     *  
     * @param jsonString 
     * @return 
     */  
    public static String[] getStringArray4Json(String jsonString) {  
  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        String[] stringArray = new String[jsonArray.size()];  
        for (int i = 0; i < jsonArray.size(); i++) {  
            stringArray[i] = jsonArray.getString(i);  
  
        }  
  
        return stringArray;  
    }  
  
  
    /** 
     * 從json數組中解析出javaLong型對象數組 
     *  
     * @param jsonString 
     * @return 
     */  
    public static Long[] getLongArray4Json(String jsonString) {  
  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        Long[] longArray = new Long[jsonArray.size()];  
        for (int i = 0; i < jsonArray.size(); i++) {  
            longArray[i] = jsonArray.getLong(i);  
  
        }  
        return longArray;  
    }  
  
  
    /** 
     * 從json數組中解析出java Integer型對象數組 
     *  
     * @param jsonString 
     * @return 
     */  
    public static Integer[] getIntegerArray4Json(String jsonString) {  
  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        Integer[] integerArray = new Integer[jsonArray.size()];  
        for (int i = 0; i < jsonArray.size(); i++) {  
            integerArray[i] = jsonArray.getInt(i);  
  
        }  
        return integerArray;  
    }  
  

    /** 
     * 從json數組中解析出java Integer型對象數組 
     *  
     * @param jsonString 
     * @return 
     */  
    public static Double[] getDoubleArray4Json(String jsonString) {  
  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        Double[] doubleArray = new Double[jsonArray.size()];  
        for (int i = 0; i < jsonArray.size(); i++) {  
            doubleArray[i] = jsonArray.getDouble(i);  
  
        }  
        return doubleArray;  
    }  
  
  
    /** 
     * 將java對象轉換成json字符串 
     *  
     * @param javaObj 
     * @return 
     */  
    public static String getJsonString4JavaPOJO(Object javaObj) {  
  
        JSONObject json;  
        json = JSONObject.fromObject(javaObj);  
        return json.toString();  
  
    }  
  

}  