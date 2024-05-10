package com.wybase.trans.common.excel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weiyu
 * @date 2024/5/10
 */
@Data
public class ExcelResult<T> {

    /**
     * excel 数据
     */
    private List<T> list = new ArrayList<>();

    /**
     * excel 错误数据
     */
    private List<String> errorList = new ArrayList<>();
}
