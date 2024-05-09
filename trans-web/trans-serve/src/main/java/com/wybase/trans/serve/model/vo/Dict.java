package com.wybase.trans.serve.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author weiyu
 * @date 2024/5/9
 */
@Data
public class Dict {

    @ExcelProperty(value = "字典名称")
    private String remark;
    @ExcelProperty(value = "字典类型")
    private String enumId;
    @ExcelProperty(value = "字典键")
    private String keyNm;
}
