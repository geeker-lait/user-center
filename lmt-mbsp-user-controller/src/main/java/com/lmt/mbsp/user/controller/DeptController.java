package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.GroupQuery;
import com.lmt.mbsp.user.biz.GroupDeptBiz;

import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.service.GroupService;
import com.lmt.mbsp.user.vo.dept.*;
import com.lmt.mbsp.user.vo.group.GroupInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * 描述: 组-部门
 * 作者: Tangsm
 * 创建时间: 2018-06-27 16:39.
 */
@RestController
@RequestMapping("/dept")
public class DeptController implements CrudController<Group,Long,GroupQuery,GroupInfo> {
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
    @GetMapping("/selById/{groupId}")
    @ApiOperation(value = "后台-获取指定公司及其部门信息（返回树）", responseReference = "get")
    public  ResponseMessage<List<DeptTreeInfo>> selById(@PathVariable Long groupId) throws Exception{
        return ok(groupDeptBiz.selById(groupId));
    }

    @GetMapping("/all")
    @ApiOperation(value = "后台-获取内部公司及其部门信息（返回树）", responseReference = "get")
    public ResponseMessage<List<DeptTreeInfo>> all() throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        return ok(groupDeptBiz.selById(context.getParentGroupId()));
    }

    @GetMapping("/selByName/{name}")
    @ApiOperation(value = "后台-根据名称模糊查询内部信息", responseReference = "get")
    public ResponseMessage<List<GroupInfo>> selByName(@PathVariable String name) throws Exception{
        return ok(groupDeptBiz.selByGradeAndName(0, name));
    }

    @GetMapping("/checkName/{groupId}/{name}")
    @ApiOperation(value = "后台-根据公司ID及部门名称查询该部门是否存在", responseReference = "get")
    public ResponseMessage<Boolean> checkDeptName(@PathVariable Long groupId, @PathVariable String name) throws Exception{
        return ok(groupDeptBiz.selByGroupIdAndName(groupId, name));
    }

    @GetMapping("/toAdd/{pid}")
    @ApiOperation(value = "后台-进入新增部门页面(参数pid不填代表新增一级部门)", responseReference = "get")
    public  ResponseMessage<List<GroupInfo>> toAddDept(@PathVariable Long pid) throws Exception{
        return ok(groupDeptBiz.toAddDept(pid));
    }

    @PostMapping("/add")
    @ApiOperation(value = "后台-新增部门", responseReference = "post")
    public  ResponseMessage<Long> addDept(@RequestBody AddDeptInfo info) throws Exception{
        return ok(groupDeptBiz.addDept(info));
    }

    @GetMapping("/toEdit/{deptId}")
    @ApiOperation(value = "后台-进入编辑部门页面", responseReference = "get")
    public  ResponseMessage<ToEditDeptInfo> toEditDept(@PathVariable Long deptId) throws Exception{
        return ok(groupDeptBiz.toEditDept(deptId));
    }

    @PostMapping("/edit")
    @ApiOperation(value = "后台-编辑部门", responseReference = "post")
    public  ResponseMessage<EditDeptInfo> editDept(@RequestBody EditDeptInfo info) throws Exception{
        groupDeptBiz.editDept(info);

        return ok(info);
    }

    @GetMapping("/detail/{deptId}")
    @ApiOperation(value = "后台-部门详细", responseReference = "get")
    public  ResponseMessage<DeptDetailInfo> detailDept(@PathVariable Long deptId) throws Exception{
        return ok(groupDeptBiz.detailDept(deptId));
    }

    @PutMapping("/disable/{deptId}")
    @ApiOperation(value = "后台-禁用部门", responseReference = "put")
    public  ResponseMessage<Long> disableDept(@PathVariable Long deptId) throws Exception{
        groupDeptBiz.disable(deptId);

        return ok(deptId);
    }

    @PutMapping("/unDisable/{deptId}")
    @ApiOperation(value = "后台激活部门", responseReference = "put")
    public  ResponseMessage<Long> unDisableDept(@PathVariable Long deptId) throws Exception{
        groupDeptBiz.unDisable(deptId);

        return ok(deptId);
    }
}
