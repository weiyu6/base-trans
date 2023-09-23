package com.wybase.trans.serve.security;

import com.wybase.trans.serve.model.entity.generate.Menu;
import com.wybase.trans.serve.service.IMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 * @author weiyu
 * @date 2023/9/17
 */
@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    private static final Logger logger = LoggerFactory.getLogger(DynamicAccessDecisionManager.class);
    @Autowired
    private IMenuService menuService;
    //Java自带的路径匹配工具
    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) {
        try {
            // 当接口未被配置资源时直接放行
            String url = "";
            //我现在具备的角色
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            String userId = securityUser.getUserId();
            List<Menu> menuList = menuService.menuListByUserId(userId);
            for (ConfigAttribute attribute : collection) {
                url = attribute.getAttribute();
                for (Menu menu : menuList) {
                    if (pathMatcher.match(url, menu.getPermissionValue())) {
                        return;
                    }
                }
            }
            logger.error("用户[{}]没有接口[{}]访问权限", userId, url);
        } catch (Exception e) {
            logger.error("权限校验失败", e);
            throw new AccessDeniedException("权限校验失败");
        }
        throw new AccessDeniedException("抱歉，您没有访问权限");

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
