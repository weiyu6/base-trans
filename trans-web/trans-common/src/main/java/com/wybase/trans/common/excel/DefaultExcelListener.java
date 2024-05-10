package com.wybase.trans.common.excel;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * excel导入监听
 *
 * @author weiyu
 * @date 2024/5/9
 */
public class DefaultExcelListener<T> extends AnalysisEventListener<T> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExcelListener.class);
    /**
     * excel 表头数据
     */
    private Map<Integer, String> headMap;

    @Getter
    private ExcelResult<T> excelResult;

    public DefaultExcelListener() {
        this.excelResult = new ExcelResult<>();
    }

    /**
     * 当处理数据转换时遇到异常时的处理逻辑。
     *
     * @param exception 遇到的异常对象，具体类型为ExcelDataConvertException或其子类。
     * @param context   分析上下文，提供额外的上下文信息，本方法未使用该参数。
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        if (exception instanceof ExcelDataConvertException excelDataConvertException) {
            // 处理Excel数据转换异常，获取异常发生的具体位置和错误信息
            Integer rowIndex = excelDataConvertException.getRowIndex();
            Integer columnIndex = excelDataConvertException.getColumnIndex();
            // 格式化错误信息，包括行号、列号和对应的表头名称
            String errMsg = StrUtil.format("第{}行-第{}列-表头{}: 解析异常", rowIndex + 1, columnIndex + 1, headMap.get(columnIndex));
            excelResult.getErrorList().add(errMsg);
        }

    }

    /**
     * 调用给定的参数对象，并将其添加到列表中。
     *
     * @param data            该参数是泛型类型，代表要添加到列表中的对象。
     * @param analysisContext 代表分析上下文的对象，用于存储或传递分析过程中的信息。在这个方法中未使用，但可能在类的其他方法中使用。
     */
    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        // 将参数对象t添加到列表中
        excelResult.getList().add(data);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
        logger.debug("解析的表头数据: {}", headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.debug("所有数据解析完成！");
    }

}
