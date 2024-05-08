package com.wybase.trans.serve.model.dto;

import com.mybatisflex.core.paginate.Page;
import com.wybase.trans.serve.model.entity.generate.EnumList;
import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2024/5/8
 */
@Data
@ToString
public class EnumOutput {

    private Page<EnumList> enumListPage;
}
