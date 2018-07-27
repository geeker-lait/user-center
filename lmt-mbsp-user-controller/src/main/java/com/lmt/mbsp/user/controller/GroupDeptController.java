package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.GroupQuery;
import com.lmt.mbsp.user.biz.GroupDeptBiz;

import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.service.GroupService;
import com.lmt.mbsp.user.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * 描述: 组-内部公司/部门
 * 作者: Tangsm
 * 创建时间: 2018-06-27 16:39.
 */
@RestController
@RequestMapping("/group-dept")
public class GroupDeptController implements CrudController<Group,Long,GroupQuery,GroupInfo> {
    private GroupService groupService;
    private GroupDeptBiz groupDeptBiz;

    @Autowired
    public void GroupDeptController(GroupService groupService, GroupDeptBiz groupDeptBiz){
        this.groupService = groupService;
        this.groupDeptBiz = groupDeptBiz;
    }

    @Override
    public CrudService<Group, Long> getService() {
        return groupService;
    }

    /****************************************************************************************** 后台-部门 ******************************************************************/
    @GetMapping("/cms/dept/selById/{id}")
    @ApiOperation(value = "后台-获取指定公司及其部门信息（返回树）", responseReference = "get")
    public  ResponseMessage<List<GroupInfo>> selById(@PathVariable Long id) throws Exception{
        return ok(groupDeptBiz.selById(id));
    }

    @GetMapping("/cms/dept/all")
    @ApiOperation(value = "后台-获取内部公司及其部门信息（返回树）", responseReference = "get")
    public ResponseMessage<List<GroupInfo>> all() throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        return ok(groupDeptBiz.selById(context.getParentGroupId()));
    }

    @GetMapping("/cms/dept/selByName/{name}")
    @ApiOperation(value = "后台-根据部门名称模糊查询内部公司及其部门信息", responseReference = "get")
    public ResponseMessage<List<GroupInfo>> selByName(@PathVariable String name) throws Exception{
        return ok(groupDeptBiz.selByGradeAndName(0, name));
    }

    @GetMapping("/cms/dept/checkName/{groupId}/{name}")
    @ApiOperation(value = "后台-根据公司ID及部门名称查询该部门是否存在", responseReference = "get")
    public ResponseMessage<Boolean> checkDeptName(@PathVariable Long groupId, @PathVariable String name) throws Exception{
        return ok(groupDeptBiz.selByGroupIdAndName(groupId, name));
    }

    @GetMapping("/cms/dept/toAdd/{parentCode}")
    @ApiOperation(value = "后台-进入新增部门页面", responseReference = "get")
    public  ResponseMessage<ToAddDeptInfo> toAddDept(@PathVariable String parentCode) throws Exception{
        return ok(groupDeptBiz.toAddDept(parentCode));
    }

    @PostMapping("/cms/dept/add")
    @ApiOperation(value = "后台-新增部门", responseReference = "post")
    public  ResponseMessage<Long> addDept(@RequestBody AddDeptInfo info) throws Exception{
        return ok(groupDeptBiz.addDept(info));
    }

    @GetMapping("/cms/dept/toEdit/{id}")
    @ApiOperation(value = "后台-进入编辑部门页面", responseReference = "get")
    public  ResponseMessage<ToEditDeptInfo> toEditDept(@PathVariable Long id) throws Exception{
        return ok(groupDeptBiz.toEditDept(id));
    }

    @PostMapping("/cms/dept/edit")
    @ApiOperation(value = "后台-编辑部门", responseReference = "post")
    public  ResponseMessage<EditDeptInfo> editDept(@RequestBody EditDeptInfo info) throws Exception{
        groupDeptBiz.editDept(info);

        return ok(info);
    }

    @GetMapping("/cms/dept/detail/{id}")
    @ApiOperation(value = "后台-部门详细", responseReference = "get")
    public  ResponseMessage<DeptDetailInfo> detailDept(@PathVariable Long id) throws Exception{
        return ok(groupDeptBiz.detailDept(id));
    }

    @PutMapping("/cms/dept/disable/{id}")
    @ApiOperation(value = "后台-禁用部门", responseReference = "put")
    public  ResponseMessage<Long> disableDept(@PathVariable Long id) throws Exception{
        groupDeptBiz.disable(id);

        return ok(id);
    }

    @PutMapping("/cms/dept/unDisable/{id}")
    @ApiOperation(value = "后台激活部门", responseReference = "put")
    public  ResponseMessage<Long> unDisableDept(@PathVariable Long id) throws Exception{
        groupDeptBiz.unDisable(id);

        return ok(id);
    }

    /****************************************************************************************** 后台-内部公司 ******************************************************************/

    @GetMapping("/cms/company/checkName/{name}")
    @ApiOperation(value = "后台-根据公司名称查询该公司是否已经存在", responseReference = "get")
    public ResponseMessage<Boolean> checkCompanyName(@PathVariable String name) throws Exception{
        return ok(groupDeptBiz.selByGroupName(name));
    }

    @GetMapping("/cms/company/toAdd/{parentId}")
    @ApiOperation(value = "后台-进入新增公司页面", responseReference = "get")
    public  ResponseMessage<List<GroupInfo>> toAddCompany(@PathVariable Long parentId) throws Exception{
        return ok(groupDeptBiz.toAddCompany(parentId));
    }

    @PostMapping("/cms/company/add")
    @ApiOperation(value = "后台-新增公司", responseReference = "post")
    public  ResponseMessage<Long> addCompany(@RequestBody AddInnerCompanyInfo info) throws Exception{
        return ok(groupDeptBiz.addCompany(info.getParentId(), info.getName()));
    }

    @GetMapping("/cms/company/toEdit/{id}")
    @ApiOperation(value = "后台-进入编辑公司页面", responseReference = "get")
    public  ResponseMessage<InnerCompanyDetailInfo> toEditCompany(@PathVariable Long id) throws Exception{
        return ok(groupDeptBiz.detailCompany(id));
    }

    @PostMapping("/cms/company/edit")
    @ApiOperation(value = "后台-编辑部门", responseReference = "post")
    public  ResponseMessage<EditInnerCompanyInfo> editCompany(@RequestBody EditInnerCompanyInfo info) throws Exception{
        groupDeptBiz.editCompany(info.getId(), info.getName());

        return ok(info);
    }

    @GetMapping("/cms/company/detail/{id}")
    @ApiOperation(value = "后台-公司详细", responseReference = "get")
    public  ResponseMessage<InnerCompanyDetailInfo> detailCompany(@PathVariable Long id) throws Exception{
        return ok(groupDeptBiz.detailCompany(id));
    }

    // 关联子公司

    @PutMapping("/cms/company/disable/{id}")
    @ApiOperation(value = "后台-禁用部门", responseReference = "put")
    public  ResponseMessage<Long> disableCompany(@PathVariable Long id) throws Exception{
        groupDeptBiz.disable(id);

        return ok(id);
    }

    @PutMapping("/cms/company/unDisable/{id}")
    @ApiOperation(value = "后台激活部门", responseReference = "put")
    public  ResponseMessage<Long> unDisableCompany(@PathVariable Long id) throws Exception{
        groupDeptBiz.unDisable(id);

        return ok(id);
    }
}
