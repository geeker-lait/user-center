package com.lmt.mbsp.user.front.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.mbsp.user.front.biz.CompanyBiz;
import com.lmt.mbsp.user.front.biz.LoginBiz;
import com.lmt.mbsp.user.front.biz.vo.LoginContext;
import com.lmt.mbsp.user.vo.company.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/*
 * 描述：组-企业商户
 * 作者：Tangsm
 * 创建时间：2018-07-24 14:00:41
 */
@RestController
@RequestMapping("/company")
public class CompanyController{
    private CompanyBiz companyBiz;
    private LoginBiz loginBiz;

    @Autowired
    public CompanyController(CompanyBiz companyBiz, LoginBiz loginBiz){
        this.companyBiz = companyBiz;
        this.loginBiz = loginBiz;
    }

    /****************************************************************************************** 用户中心-企业商户 ******************************************************************/
    @GetMapping("/checkName/{name}")
    @ApiOperation(value = "后台-根据公司名称查询该公司是否已经存在", responseReference = "get")
    public ResponseMessage<Boolean> checkCompanyName(@PathVariable String name) throws Exception{
        return ok(companyBiz.checkCompanyName(name));
    }

    @PostMapping("/apply")
    @ApiOperation(value = "用户中心-申请企业商户", responseReference = "post")
    public ResponseMessage<Long> apply(@RequestBody AddCompanyInfo info)  throws Exception{
        return ok(companyBiz.add(info));
    }

    @GetMapping("/toEdit")
    @ApiOperation(value = "用户中心-进入编辑企业商户页面", responseReference = "get")
    public ResponseMessage<ToEditCompanyInfo> toEdit()  throws Exception{
        LoginContext context = loginBiz.getLoginContext();
        return ok(companyBiz.toEdit(context.getCompanyId()));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "用户中心-编辑企业商户", responseReference = "get")
    public ResponseMessage<EditCompanyInfo> edit(@RequestBody EditCompanyInfo info)  throws Exception{
        companyBiz.edit(info);

        return ok(info);
    }
}
