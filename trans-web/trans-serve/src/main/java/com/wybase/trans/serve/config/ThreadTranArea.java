package com.wybase.trans.serve.config;

import cn.hutool.core.map.MapUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 交易数据缓存池
 * @author weiyu
 * @date 2023/8/1
 */
public class ThreadTranArea {
    // 全局字段缓存池
    private Map<String, Object> fieldMap;

    public ThreadTranArea() {
        super();
        fieldMap = new ConcurrentHashMap<>();
    }

    /**
     * 缓存池初始化
     */
    public void cleanMap() {
        if (fieldMap != null) {
            fieldMap.clear();
        }
    }

    /**
     * 缓存池添加公共字段值
     * @param key
     * @param val
     */
    public void setField(String key, Object val) {
        fieldMap.put(key, val);
    }

    /**
     * 从缓存池中取公共字段值
     * @param key
     * @return
     */
    public Object getField(String key) {
        if (MapUtil.isNotEmpty(fieldMap)) {
            return fieldMap.get(key);
        }
        return null;
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

}
