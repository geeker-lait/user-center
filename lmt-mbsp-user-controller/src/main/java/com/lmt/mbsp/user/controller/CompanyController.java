package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.CompanyBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.GroupQuery;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.service.GroupService;
import com.lmt.mbsp.user.vo.company.*;
import com.lmt.mbsp.user.vo.group.GroupInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/*
 * 描述：组-企业商户
 * 作者：Tangsm
 * 创建时间：2018-07-24 14:00:41
 */
@RestController
@RequestMapping("/company")
public class CompanyController implements CrudController<Group,Long,GroupQuery,GroupInfo> {
    private GroupService groupService;
    private CompanyBiz companyBiz;

    @Autowired
    public void IndexController(GroupService groupService, CompanyBiz companyBiz){
        this.groupService = groupService;
        this.companyBiz = companyBiz;
    }

    @Override
    public CrudService<Group, Long> getService() {
        return groupService;
    }

    /****************************************************************************************** 后台-企业商户 ******************************************************************/
    @PostMapping("/add")
    @ApiOperation(value = "后台-新增企业商户", responseReference = "post")
    public ResponseMessage<Long> add(@RequestBody AddCompanyInfo info)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setUserId(context.getUserId());

        return ok(companyBiz.add(info));
    }

    @GetMapping("/detail/{groupId}")
    @ApiOperation(value = "后台-企业商户详细", responseReference = "get")
    public  ResponseMessage<CompanyDetailInfo> detail(@PathVariable Long groupId) throws Exception{
        return ok(companyBiz.detail(groupId));
    }

    @GetMapping("/roles/{groupId}")
    @ApiOperation(value = "后台-企业商户角色权限", responseReference = "get")
    public  ResponseMessage<List<RoleInfo>> searchRoles(@PathVariable Long groupId) throws Exception{
        return ok(companyBiz.searchRoles(groupId));
    }

    @GetMapping("/toEdit/{groupId}")
    @ApiOperation(value = "后台-进入编辑企业商户页面", responseReference = "get")
    public ResponseMessage<ToEditCompanyInfo> toEdit(@PathVariable Long groupId)  throws Exception{
        return ok(companyBiz.toEdit(groupId));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "后台-编辑企业商户", responseReference = "get")
    public ResponseMessage<EditCompanyInfo> edit(@RequestBody EditCompanyInfo info)  throws Exception{
        companyBiz.edit(info);

        return ok(info);
    }

    @GetMapping("/toAuthorize/{groupId}")
    @ApiOperation(value = "后台-进入企业商户授权页面", responseReference = "get")
    public  ResponseMessage<ToCompanyAuthorizeInfo> toAuthorize(@PathVariable Long groupId)  throws Exception{
        return ok(companyBiz.toAuthorize(groupId));
    }

    @PutMapping("/authorize")
    @ApiOperation(value = "后台-企业商户授权", responseReference = "put")
    public  ResponseMessage<SaveCompanyAuthorizeInfo> authorize(@RequestBody SaveCompanyAuthorizeInfo info)  throws Exception{
        companyBiz.authorize(info);

        return ok(info);
    }

    @PutMapping("/disable/{groupId}")
    @ApiOperation(value = "后台-禁用企业商户", responseReference = "put")
    public  ResponseMessage<Long> disableDept(@PathVariable Long groupId) throws Exception{
        companyBiz.disable(groupId);

        return ok(groupId);
    }

    @PutMapping("/unDisable/{groupId}")
    @ApiOperation(value = "后台-激活企业商户", responseReference = "put")
    public  ResponseMessage<Long> unDisableDept(@PathVariable Long groupId) throws Exception{
        companyBiz.unDisable(groupId);

        return ok(groupId);
    }

    @PutMapping("/audit/{groupId}/{type}")
    @ApiOperation(value = "后台-审核企业商户", responseReference = "put")
    public  ResponseMessage<Long> audit(@PathVariable Long groupId, @PathVariable Integer type) throws Exception{
        companyBiz.audit(groupId, type);

        return ok(groupId);
    }

    /****************************************************************************************** 用户中心-企业商户 ******************************************************************/
    @PostMapping("/apply")
    @ApiOperation(value = "用户中心-申请企业商户", responseReference = "post")
    public ResponseMessage<Long> csAdd(@RequestBody AddCompanyInfo info)  throws Exception{
        return ok(companyBiz.add(info));
    }

    @GetMapping("/toEditApply")
    @ApiOperation(value = "用户中心-进入编辑企业商户页面", responseReference = "get")
    public ResponseMessage<ToEditCompanyInfo> csToEdit()  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        return ok(companyBiz.toEdit(context.getGroup().getId()));
    }

    @PutMapping("/editApply")
    @ApiOperation(value = "用户中心-编辑企业商户", responseReference = "get")
    public ResponseMessage<EditCompanyInfo> csEdit(@RequestBody EditCompanyInfo info)  throws Exception{
        companyBiz.edit(info);

        return ok(info);
    }
}
