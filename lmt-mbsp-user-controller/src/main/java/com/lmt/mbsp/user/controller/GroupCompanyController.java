package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.entity.PagerResult;
import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.GroupCompanyBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.GroupQuery;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.service.GroupService;
import com.lmt.mbsp.user.vo.*;
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
@RequestMapping("/group-company")
public class GroupCompanyController implements CrudController<Group,Long,GroupQuery,GroupInfo> {
    private GroupService groupService;
    private GroupCompanyBiz groupCompanyBiz;

    @Autowired
    public void IndexController(GroupService groupService, GroupCompanyBiz groupCompanyBiz){
        this.groupService = groupService;
        this.groupCompanyBiz = groupCompanyBiz;
    }

    @Override
    public CrudService<Group, Long> getService() {
        return groupService;
    }

    /****************************************************************************************** 后台-企业商户 ******************************************************************/
    @PostMapping("/cms/add")
    @ApiOperation(value = "后台-新增企业商户", responseReference = "post")
    public ResponseMessage<Long> add(@RequestBody AddCompanyInfo info)  throws Exception{
        return ok(groupCompanyBiz.add(info));
    }

    @GetMapping("/cms/detail/{id}")
    @ApiOperation(value = "后台-企业商户详细", responseReference = "get")
    public  ResponseMessage<CompanyDetailInfo> detail(@PathVariable Long id) throws Exception{
        return ok(groupCompanyBiz.detail(id));
    }

    @GetMapping("/cms/roles/{groupId}")
    @ApiOperation(value = "后台-企业商户角色权限", responseReference = "get")
    public  ResponseMessage<List<RoleInfo>> searchRoles(@PathVariable Long groupId) throws Exception{
        return ok(groupCompanyBiz.searchRoles(groupId));
    }

    @GetMapping("/cms/toEdit/{id}")
    @ApiOperation(value = "后台-进入编辑企业商户页面", responseReference = "get")
    public ResponseMessage<ToEditCompanyInfo> toEdit(@PathVariable Long id)  throws Exception{
        return ok(groupCompanyBiz.toEdit(id));
    }

    @PutMapping("/cms/edit")
    @ApiOperation(value = "后台-编辑企业商户", responseReference = "get")
    public ResponseMessage<EditCompanyInfo> edit(@RequestBody EditCompanyInfo info)  throws Exception{
        groupCompanyBiz.edit(info);

        return ok(info);
    }

    @GetMapping("/cms/toAuthorize/{id}")
    @ApiOperation(value = "后台-进入企业商户授权页面", responseReference = "get")
    public  ResponseMessage<ToCompanyAuthorizeInfo> toAuthorize(@PathVariable Long id)  throws Exception{
        return ok(groupCompanyBiz.toAuthorize(id));
    }

    @PutMapping("/cms/authorize")
    @ApiOperation(value = "后台-企业商户授权", responseReference = "put")
    public  ResponseMessage<SaveCompanyAuthorizeInfo> authorize(@RequestBody SaveCompanyAuthorizeInfo info)  throws Exception{
        groupCompanyBiz.authorize(info);

        return ok(info);
    }

    @PutMapping("/cms/disable/{id}")
    @ApiOperation(value = "后台-禁用企业商户", responseReference = "put")
    public  ResponseMessage<Long> disableDept(@PathVariable Long id) throws Exception{
        groupCompanyBiz.disable(id);

        return ok(id);
    }

    @PutMapping("/cms/unDisable/{id}")
    @ApiOperation(value = "后台-激活企业商户", responseReference = "put")
    public  ResponseMessage<Long> unDisableDept(@PathVariable Long id) throws Exception{
        groupCompanyBiz.unDisable(id);

        return ok(id);
    }

    @PutMapping("/cms/audit/{id}/{type}")
    @ApiOperation(value = "后台-审核企业商户", responseReference = "put")
    public  ResponseMessage<Long> audit(@PathVariable Long id, @PathVariable Integer type) throws Exception{
        groupCompanyBiz.audit(id, type);

        return ok(id);
    }

    /****************************************************************************************** 用户中心-企业商户 ******************************************************************/
    @PostMapping("/cs/add")
    @ApiOperation(value = "后台-新增企业商户", responseReference = "post")
    public ResponseMessage<Long> csAdd(@RequestBody AddCompanyInfo info)  throws Exception{
        return ok(groupCompanyBiz.add(info));
    }

    @GetMapping("/cs/toEdit")
    @ApiOperation(value = "后台-进入编辑企业商户页面", responseReference = "get")
    public ResponseMessage<ToEditCompanyInfo> csToEdit()  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        return ok(groupCompanyBiz.toEdit(context.getGroup().getId()));
    }

    @PutMapping("/cs/edit")
    @ApiOperation(value = "后台-编辑企业商户", responseReference = "get")
    public ResponseMessage<EditCompanyInfo> csEdit(@RequestBody EditCompanyInfo info)  throws Exception{
        groupCompanyBiz.edit(info);

        return ok(info);
    }
}
