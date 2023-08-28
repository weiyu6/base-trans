package com.wybase.trans.serve.service.impl;

import com.wybase.trans.serve.dto.IndexOutput;
import com.wybase.trans.serve.service.IIndexService;
import com.wybase.trans.serve.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后管首页服务
 * @author weiyu
 * @date 2023/8/28
 */
@Service
public class IndexServiceImpl implements IIndexService {
    private static final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public IndexOutput init() {
        logger.debug("IndexServiceImpl.init begin >>>>>>>>>>>>>>>>>>>");
        IndexOutput serviceOutput = new IndexOutput();
        // 查询用户总数
        long userCount = userInfoService.userCount();
        // 查询博客总数
//        long blogCount = blogService.blogCount();
        long blogCount = 0;
        //TODO 查询评论总数
        // long commentCount = commentService.blogCount();
        serviceOutput.setUserCount(userCount);
        serviceOutput.setBlogCount(blogCount);
        logger.debug("IndexServiceImpl.init end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }
}
