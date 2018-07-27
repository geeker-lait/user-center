package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.account.AccountMapper;
import com.lmt.mbsp.user.dao.mapper.group.GroupImgMapper;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.group.GroupImg;
import com.lmt.mbsp.user.service.AccountService;
import com.lmt.mbsp.user.service.GroupImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 描述：企业图片
 * 作者：Tangsm
 * 创建时间：2018-07-24 17:21:49
 */
@Service
public class GroupImgServiceImpl extends BaseServiceImpl<GroupImg, Long> implements GroupImgService {
    @Autowired
    private GroupImgMapper groupImgMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return groupImgMapper;
    }

    @Override
    public GroupImg createEntity() {
        return new GroupImg();
    }

    @Override
    public List<GroupImg> selectByGroupId(Long groupId)  throws ServiceException{
        return groupImgMapper.selectByGroupId(groupId);
    }

    @Override
    public void deleteByGroupId(Long groupId) throws ServiceException {
        groupImgMapper.deleteByGroupId(groupId);
    }
}
