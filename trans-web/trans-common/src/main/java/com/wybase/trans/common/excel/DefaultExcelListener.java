package com.wybase.trans.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * excel导入监听
 * @author weiyu
 * @date 2024/5/9
 */
public class DefaultExcelListener<T> extends AnalysisEventListener<T> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExcelListener.class);
    /**
     * excel 表头数据
     */
    private Map<Integer, String> headMap;

    /**
     * excel 数据
     */
    @Getter
    private List<T> list;

    public DefaultExcelListener() {
        this.list = new ArrayList<>();
    }
    /**
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        super.onException(exception, context);
    }

    /**
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
        logger.debug("解析到一条表头数据: {}", JSONObject.toJSONString(headMap));
    }

    /**
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}
