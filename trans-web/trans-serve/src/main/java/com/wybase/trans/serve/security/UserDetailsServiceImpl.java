package com.wybase.trans.serve.security;

import cn.hutool.core.util.ObjectUtil;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.model.entity.generate.UserInfo;
import com.wybase.trans.serve.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author weiyu
 * @date 2023/9/16
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.getById(userId);
        if (ObjectUtil.isEmpty(userInfo)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String[] roleIds = userInfo.getRoleId().split("\\|");
        List<String> roleIdList = new ArrayList<>(Arrays.asList(roleIds));
        // 判断此用户是否启用
        boolean enabled = StringUtils.equals(userInfo.getRecdStat(), TransConsts.RECD_STAT_0);
        return new SecurityUser(
                userInfo.getUserId(),
                userInfo.getUserNm(),
                userInfo.getPassWord(),
                enabled,
                roleIdList
        );
    }
}
