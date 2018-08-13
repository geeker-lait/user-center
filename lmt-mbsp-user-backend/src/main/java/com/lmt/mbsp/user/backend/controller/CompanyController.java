package com.lmt.mbsp.user.backend.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.QueryController;
import com.lmt.mbsp.user.backend.biz.CompanyBiz;
import com.lmt.mbsp.user.backend.biz.vo.LoginContext;
import com.lmt.mbsp.user.dto.CompanyQuery;
import com.lmt.mbsp.user.entity.group.Company;
import com.lmt.mbsp.user.service.CompanyService;
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
public class CompanyController implements QueryController<Company,Long,CompanyQuery> {
    private CompanyService companyService;
    private CompanyBiz companyBiz;

    @Autowired
    public CompanyController(CompanyService companyService, CompanyBiz companyBiz){
        this.companyService = companyService;
        this.companyBiz = companyBiz;
    }

    @Override
    public CrudService<Company, Long> getService() {
        return companyService;
    }

    /****************************************************************************************** 后台-企业商户 ******************************************************************/
    @GetMapping("/checkName/{name}")
    @ApiOperation(value = "后台-根据公司名称查询该公司是否已经存在", responseReference = "get")
    public ResponseMessage<Boolean> checkCompanyName(@PathVariable String name) throws Exception{
        return ok(companyBiz.checkCompanyName(name));
    }

    @PostMapping("/add")
    @ApiOperation(value = "后台-新增企业商户", responseReference = "post")
    public ResponseMessage<Long> add(@RequestBody AddCompanyInfo info)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setUserId(context.getUserId());

        return ok(companyBiz.add(info));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "后台-企业商户详细", responseReference = "get")
    public  ResponseMessage<CompanyDetailInfo> detail(@PathVariable Long id) throws Exception{
        return ok(companyBiz.detail(id));
    }

    @GetMapping("/toEdit/{id}")
    @ApiOperation(value = "后台-进入编辑企业商户页面", responseReference = "get")
    public ResponseMessage<ToEditCompanyInfo> toEdit(@PathVariable Long id)  throws Exception{
        return ok(companyBiz.toEdit(id));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "后台-编辑企业商户", responseReference = "get")
    public ResponseMessage<EditCompanyInfo> edit(@RequestBody EditCompanyInfo info)  throws Exception{
        companyBiz.edit(info);

        return ok(info);
    }

    @GetMapping("/toAuthorize/{id}")
    @ApiOperation(value = "后台-进入企业商户授权页面", responseReference = "get")
    public  ResponseMessage<ToCompanyAuthorizeInfo> toAuthorize(@PathVariable Long id)  throws Exception{
        return ok(companyBiz.toAuthorize(id));
    }

    @PutMapping("/authorize")
    @ApiOperation(value = "后台-企业商户授权", responseReference = "put")
    public  ResponseMessage<SaveCompanyAuthorizeInfo> authorize(@RequestBody SaveCompanyAuthorizeInfo info)  throws Exception{
        companyBiz.authorize(info);

        return ok(info);
    }

    @PutMapping("/disable/{id}")
    @ApiOperation(value = "后台-禁用企业商户", responseReference = "put")
    public  ResponseMessage<Long> disableDept(@PathVariable Long id) throws Exception{
        companyBiz.disable(id);

        return ok(id);
    }

    @PutMapping("/unDisable/{id}")
    @ApiOperation(value = "后台-激活企业商户", responseReference = "put")
    public  ResponseMessage<Long> unDisableDept(@PathVariable Long id) throws Exception{
        companyBiz.unDisable(id);

        return ok(id);
    }

    @PutMapping("/audit/{id}/{type}")
    @ApiOperation(value = "后台-审核企业商户", responseReference = "put")
    public  ResponseMessage<Long> audit(@PathVariable Long id, @PathVariable Integer type) throws Exception{
        companyBiz.audit(id, type);

        return ok(id);
    }
}
