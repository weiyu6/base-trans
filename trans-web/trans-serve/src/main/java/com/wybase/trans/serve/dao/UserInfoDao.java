package com.wybase.trans.serve.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.wybase.trans.serve.entity.generate.UserInfo;
import com.wybase.trans.serve.entity.table.UserInfoTableDef;
import com.wybase.trans.serve.mapper.generate.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户信息查询
 * @author weiyu
 * @date 2023/8/5
 */
@Component
public class UserInfoDao {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo queryByUserNm(String username) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where(UserInfoTableDef.USER_INFO.USER_NM.eq(username));
        return userInfoMapper.selectOneByQuery(wrapper);
    }
}
