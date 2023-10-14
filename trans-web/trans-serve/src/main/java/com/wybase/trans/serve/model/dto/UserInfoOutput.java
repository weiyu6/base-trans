package com.wybase.trans.serve.model.dto;

import com.mybatisflex.core.paginate.Page;
import com.wybase.trans.serve.model.entity.custom.UserInfoExtend;
import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2023/10/15
 */
@Data
@ToString
public class UserInfoOutput {
    private Page<UserInfoExtend> userExtendPageInfo;

    private UserInfoExtend userExtend;
}
