package com.wybase.trans.serve.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.ResultCodeEnum;
import com.wybase.trans.common.consts.TransConsts;
import com.wybase.trans.serve.config.TransContext;
import com.wybase.trans.serve.dao.UserInfoDao;
import com.wybase.trans.serve.mapper.generate.UserInfoMapper;
import com.wybase.trans.serve.model.dto.UserInfoInput;
import com.wybase.trans.serve.model.dto.UserInfoOutput;
import com.wybase.trans.serve.model.entity.custom.UserInfoExtend;
import com.wybase.trans.serve.model.entity.generate.UserInfo;
import com.wybase.trans.serve.model.entity.generate.table.UserInfoTableDef;
import com.wybase.trans.serve.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用户信息表 服务层实现。
 * @author weiyu
 * @since 2023-08-05
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public long userCount() {

        return userInfoDao.count();
    }

    /**
     * 分页查询用户列表
     * @param input
     * @return
     */
    @Override
    public UserInfoOutput userInfoListPage(UserInfoInput input) {
        logger.debug("UserInfoServiceImpl.userInfoListPage begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("userInfoListPage,input:{}", input);
        UserInfoOutput serviceOutput = new UserInfoOutput();
        int pageNum = input.getPageNum();
        int pageSize = input.getPageSize();
        String userNm = input.getUserNm();// 用户名
        String userTag = input.getUserTag();// 用户标签
        String commentStat = input.getCommentStat();// 评论状态
        // 查询记录状态正常的用户列表
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(UserInfoTableDef.USER_INFO.USER_NM.like(userNm).when(StringUtils::isNotBlank))
                .and(UserInfoTableDef.USER_INFO.USER_TAG.eq(userTag).when(StringUtils.isNotBlank(userTag)))
                .and(UserInfoTableDef.USER_INFO.COMMENT_STAT.eq(commentStat).when(StringUtils.isNotBlank(commentStat)))
                .and(UserInfoTableDef.USER_INFO.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        Page<UserInfo> page = new Page<>(pageNum, pageSize);
        Page<UserInfo> userList = page(page, queryWrapper);

        List<UserInfoExtend> userExtendList = new CopyOnWriteArrayList<>();
        for (UserInfo user : userList.getRecords()) {
            UserInfoExtend userExtend = new UserInfoExtend();
            BeanUtils.copyProperties(user, userExtend);
            String roleId = user.getRoleId();
            String[] roleIdArr = roleId.split("\\|");
            List<String> roleIdList = Arrays.asList(roleIdArr);
            userExtend.setRoleIds(roleIdList);
            userExtendList.add(userExtend);
        }
        Page<UserInfoExtend> userExtendPageInfo = new Page<>(userExtendList, pageNum, pageSize, userList.getTotalRow());
        serviceOutput.setUserExtendPageInfo(userExtendPageInfo);
        logger.debug("userInfoListPage,input:{}", serviceOutput);
        logger.debug("UserInfoServiceImpl.userInfoListPage end:<<<<<<<<<<<<<<<<<");
        return serviceOutput;
    }

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    @Override
    public UserInfoExtend userExtendById(String userId) {
        logger.debug("UserInfoServiceImpl.userExtendById begin >>>>>>>>>>>>>>>>>>>");
        logger.debug("userId：{}", userId);
        UserInfo user = getById(userId);
        logger.debug("user:{}", user);
        if (ObjectUtil.isEmpty(user)) {
            throw new TransException(ResultCodeEnum.TRAN100701);
        }
        UserInfoExtend userExtend = new UserInfoExtend();
        BeanUtils.copyProperties(user, userExtend);
        String roleId = user.getRoleId();
        String[] roleIdArr = roleId.split("\\|");
        List<String> roleIdList = Arrays.asList(roleIdArr);
        userExtend.setRoleIds(roleIdList);
        logger.debug("UserInfoServiceImpl.userExtendById end:<<<<<<<<<<<<<<<<<");
        return userExtend;
    }

    /**
     * 修改用户信息
     * @param userInput
     */
    @Override
    public void userInfoMdf(UserInfoInput userInput) {
        logger.debug("UserInfoServiceImpl.userInfoMdf begin >>>>>>>>>>>>>>>>>>>");
        // 检查用户名是否重复
        String userNm = userInput.getUserNm();
        String userId = userInput.getUserId();
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(UserInfoTableDef.USER_INFO.USER_NM.eq(userNm))
                .and(UserInfoTableDef.USER_INFO.USER_ID.ne(userId))
                .and(UserInfoTableDef.USER_INFO.RECD_STAT.eq(TransConsts.RECD_STAT_0));
        List<UserInfo> userInfoList = list(queryWrapper);
        if (ObjectUtil.isNotEmpty(userInfoList)) {
            logger.error("用户名已注册");
            throw new TransException(ResultCodeEnum.REGISTERED_USERNM_ERROR);
        }

        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(userInput, user);
        List<String> roleIds = userInput.getRoleIds();
        if (ObjectUtil.isNotEmpty(roleIds)) {
            StringBuffer sb = new StringBuffer();
            for (String id : roleIds) {
                sb.append(id);
                sb.append("|");
            }
            String roleId = sb.toString();
            user.setRoleId(roleId);
        }
        user.setTransRecdNum(TransContext.getString("TransRecdNum"));
        updateById(user, true);
        logger.debug("UserInfoServiceImpl.userInfoMdf end:<<<<<<<<<<<<<<<<<");
    }
}
