package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.GroupDeptBiz;
import com.lmt.mbsp.user.dto.GroupQuery;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.service.GroupService;
import com.lmt.mbsp.user.vo.group.AddGroupInfo;
import com.lmt.mbsp.user.vo.group.EditGroupInfo;
import com.lmt.mbsp.user.vo.group.GroupDetailInfo;
import com.lmt.mbsp.user.vo.group.GroupInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * 描述: 组-内部公司
 * 作者: Tangsm
 * 创建时间: 2018-06-27 16:39.
 */
@RestController
@RequestMapping("/group")
public class GroupController implements CrudController<Group,Long,GroupQuery,GroupInfo> {
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

    /****************************************************************************************** 后台-内部公司 ******************************************************************/

    @GetMapping("/checkName/{name}")
    @ApiOperation(value = "后台-根据公司名称查询该公司是否已经存在", responseReference = "get")
    public ResponseMessage<Boolean> checkCompanyName(@PathVariable String name) throws Exception{
        return ok(groupDeptBiz.selByGroupName(name));
    }

    @GetMapping("/toAdd/{parentId}")
    @ApiOperation(value = "后台-进入新增公司页面", responseReference = "get")
    public  ResponseMessage<List<GroupInfo>> toAddCompany(@PathVariable Long parentId) throws Exception{
        return ok(groupDeptBiz.toAddCompany(parentId));
    }

    @PostMapping("/add")
    @ApiOperation(value = "后台-新增公司", responseReference = "post")
    public  ResponseMessage<Long> addCompany(@RequestBody AddGroupInfo info) throws Exception{
        return ok(groupDeptBiz.addCompany(info.getParentId(), info.getName()));
    }

    @GetMapping("/toEdit/{groupId}")
    @ApiOperation(value = "后台-进入编辑公司页面", responseReference = "get")
    public  ResponseMessage<GroupDetailInfo> toEditCompany(@PathVariable Long groupId) throws Exception{
        return ok(groupDeptBiz.detailCompany(groupId));
    }

    @PostMapping("/edit")
    @ApiOperation(value = "后台-编辑公司", responseReference = "post")
    public  ResponseMessage<EditGroupInfo> editCompany(@RequestBody EditGroupInfo info) throws Exception{
        groupDeptBiz.editCompany(info.getId(), info.getName());

        return ok(info);
    }

    @GetMapping("/detail/{groupId}")
    @ApiOperation(value = "后台-公司详细", responseReference = "get")
    public  ResponseMessage<GroupDetailInfo> detailCompany(@PathVariable Long groupId) throws Exception{
        return ok(groupDeptBiz.detailCompany(groupId));
    }

    @PutMapping("/relation/{groupId}/{sonName}")
    @ApiOperation(value = "后台-关联子公司", responseReference = "put")
    public  ResponseMessage<Long> relationSon(@PathVariable Long groupId, @PathVariable String sonName) throws Exception{
        groupDeptBiz.relationSon(groupId, sonName);

        return ok(groupId);
    }

    @PutMapping("/disable/{groupId}")
    @ApiOperation(value = "后台-禁用公司", responseReference = "put")
    public  ResponseMessage<Long> disableCompany(@PathVariable Long groupId) throws Exception{
        groupDeptBiz.disable(groupId);

        return ok(groupId);
    }

    @PutMapping("/unDisable/{groupId}")
    @ApiOperation(value = "后台激活公司", responseReference = "put")
    public  ResponseMessage<Long> unDisableCompany(@PathVariable Long groupId) throws Exception{
        groupDeptBiz.unDisable(groupId);

        return ok(groupId);
    }
}
