package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.entity.member.Member;
import com.lmt.mbsp.user.dao.mapper.member.MemberMapper;
import com.lmt.mbsp.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 会员服务实现.
 * @作者: lijing.
 * @创建时间: 2018-06-27 17:30.
 * @版本: 1.0 .
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member,Long> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return memberMapper;
    }

    @Override
    public Member createEntity() {
        return new Member();
    }
}
