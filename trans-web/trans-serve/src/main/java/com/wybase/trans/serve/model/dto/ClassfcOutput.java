package com.wybase.trans.serve.model.dto;

import com.mybatisflex.core.paginate.Page;
import com.wybase.trans.serve.model.entity.generate.Classfc;
import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2023/10/17
 */
@Data
@ToString
public class ClassfcOutput {
    Page<Classfc> classfcPageInfo;
}
