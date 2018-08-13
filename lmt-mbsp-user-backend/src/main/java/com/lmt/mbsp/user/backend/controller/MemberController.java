//package com.lmt.mbsp.user.backend.controller;
//
//import com.lmt.framework.support.service.CrudService;
//import com.lmt.framework.support.web.controller.CrudController;
//import com.lmt.mbsp.user.dto.MemberQuery;
//import com.lmt.mbsp.user.entity.member.Member;
//import com.lmt.mbsp.user.service.MemberService;
//import com.lmt.mbsp.user.vo.member.MemberInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Auther: lait.zhang@gmail.com
// * @Tel:15801818092
// * @Date: 6/1/2018 15:58
// * @Description:
// */
//@RestController
//@RequestMapping("member")
//public class MemberController implements CrudController<Member,Long,MemberQuery,MemberInfo> {
//    @Autowired
//    private MemberService memberService;
//
//
//    @Override
//    public CrudService<Member, Long> getService() {
//        return memberService;
//    }
//
//
//
//}
