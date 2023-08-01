package com.wybase.trans.serve.config;

import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 交易上下文环境
 * @author weiyu
 * @date 2023/8/1
 */
@Component
public class TransContext implements ApplicationContextAware {

    @Getter
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 当前运行交易区域.
     */
    private static final ThreadLocal<ThreadTranArea> THREAD_TRAN_AREA = ThreadLocal.withInitial(ThreadTranArea::new);

    // 获取threadTranArea
    private static ThreadTranArea getThreadTranArea() {
        return THREAD_TRAN_AREA.get();
    }

    //初始化环境,一般在平台框架层调用,应用层禁止调用。
    public static void init() {
        getThreadTranArea().cleanMap();
    }

    /**
     * 获取交易缓存池中的数据
     */
    public static Map<String, Object> getFieldMap() {
        return getThreadTranArea().getFieldMap();
    }

    /**
     * 向交易缓存池中填充数据
     */
    public static void setFieldMap(Map<String, Object> fieldMap) {
        getThreadTranArea().setFieldMap(fieldMap);
    }

    /**
     * 缓存池添加公共字段值
     */
    public static void setField(String key, Object val) {
        getThreadTranArea().setField(key, val);
    }

    /**
     * 从缓存池中取公共字段值
     */
    public static Object getField(String key) {
        return getThreadTranArea().getField(key);
    }

    /**
     * 从缓存池中取String类型字段
     */
    public static String getString(String key) {
        Object object = getField(key);
        if (object == null) {
            return null;
        } else {
            return object instanceof String ? (String) object : object.toString();
        }
    }

    /**
     * 从缓存池中取Integer类型字段
     */
    public static Integer getInteger(String name) {
        Object object = getField(name);
        if (object == null) {
            return null;
        } else if (object instanceof Integer) {
            return (Integer) object;
        } else if (object instanceof String) {
            return ((String) object).isEmpty() ? null : new Integer((String) object);
        } else if (object instanceof Number) {
            return ((Number) object).intValue();
        } else {
            throw new TransException(ResultCodeEnum.ERROR);
        }
    }

    /**
     * 从缓存池中取Long类型字段
     */
    public static Long getLong(String name) {
        Object object = getField(name);
        if (object == null) {
            return null;
        } else if (object instanceof Long) {
            return (Long) object;
        } else if (object instanceof String) {
            return ((String) object).isEmpty() ? null : new Long((String) object);
        } else if (object instanceof Number) {
            return ((Number) object).longValue();
        } else {
            throw new TransException(ResultCodeEnum.ERROR);
        }
    }

    /**
     * 从缓存池中取BigDecimal类型字段
     */
    public static BigDecimal getBigDecimal(String name) {
        Object object = getField(name);
        if (object == null) {
            return null;
        } else if (object instanceof BigDecimal) {
            return (BigDecimal) object;
        } else if (object instanceof String) {
            return new BigDecimal((String) object);
        } else if (object instanceof Number) {
            return BigDecimal.valueOf(((Number) object).doubleValue());
        } else {
            throw new TransException(ResultCodeEnum.ERROR);
        }
    }
}
