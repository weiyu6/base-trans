package com.wybase.trans.serve.model.dto;

import com.mybatisflex.core.paginate.Page;
import com.wybase.trans.serve.model.entity.custom.BlogExtend;
import com.wybase.trans.serve.model.entity.generate.Blog;
import lombok.Data;
import lombok.ToString;

/**
 * @author weiyu
 * @date 2023/10/17
 */
@Data
@ToString
public class BlogOutput {
    private Page<Blog> blogPageInfo;
    private Page<BlogExtend> blogExtendPageInfo;
}
