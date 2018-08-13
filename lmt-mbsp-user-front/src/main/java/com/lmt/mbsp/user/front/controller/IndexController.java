package com.lmt.mbsp.user.front.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.mbsp.user.front.biz.CompanyBiz;
import com.lmt.mbsp.user.front.biz.LoginBiz;
import com.lmt.mbsp.user.front.biz.vo.LoginContext;
import com.lmt.mbsp.user.vo.operator.IndexInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/*
 * 描述：首页
 * 作者：Tangsm
 * 创建时间：2018-07-25 11:47:31
 */
@RestController
@RequestMapping("/index")
public class IndexController{
    private CompanyBiz groupCompanyBiz;
    private LoginBiz loginBiz;

    @Autowired
    public IndexController(CompanyBiz groupCompanyBiz, LoginBiz loginBiz){
        this.groupCompanyBiz = groupCompanyBiz;
        this.loginBiz = loginBiz;
    }

    /****************************************************************************************** 用户中心-首页 ******************************************************************/

    @GetMapping("/home")
    @ApiOperation(value = "用户中心-首页", responseReference = "get")
    public ResponseMessage<IndexInfo> csIndex()  throws Exception{
        LoginContext context = loginBiz.getLoginContext();
        return ok(groupCompanyBiz.index(context.getAccountId(), context.getUserId(), context.getGroupId()));
    }
}
