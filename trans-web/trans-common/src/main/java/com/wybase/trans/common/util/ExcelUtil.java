package com.wybase.trans.common.util;

import com.alibaba.excel.EasyExcel;
import com.wybase.trans.common.excel.DefaultExcelListener;
import com.wybase.trans.common.excel.ExcelResult;

import java.io.InputStream;

/**
 * Excel工具类
 *
 * @author weiyu
 * @date 2024/5/10
 */
public class ExcelUtil {
    /**
     * 从Excel中导入数据。
     *
     * @param is    输入流，代表要读取的Excel文件
     * @param clazz 数据模型的类，用于映射Excel中的数据行
     * @return ExcelResult<T> 包含导入结果的对象，其中T为数据模型的类型
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz) {
        DefaultExcelListener<T> listener = new DefaultExcelListener<>();
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }
}
