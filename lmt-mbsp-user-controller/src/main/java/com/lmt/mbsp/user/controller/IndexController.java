package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.CompanyBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.GroupQuery;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.service.GroupService;
import com.lmt.mbsp.user.vo.group.GroupInfo;
import com.lmt.mbsp.user.vo.operator.IndexInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/*
 * 描述：首页
 * 作者：Tangsm
 * 创建时间：2018-07-25 11:47:31
 */
@RestController
@RequestMapping("/index")
public class IndexController implements CrudController<Group,Long,GroupQuery,GroupInfo> {
    private GroupService groupService;
    private CompanyBiz groupCompanyBiz;

    @Autowired
    public void IndexController(GroupService groupService, CompanyBiz groupCompanyBiz){
        this.groupService = groupService;
        this.groupCompanyBiz = groupCompanyBiz;
    }

    @Override
    public CrudService<Group, Long> getService() {
        return groupService;
    }

    /****************************************************************************************** 用户中心-首页 ******************************************************************/

    @GetMapping("csIndex")
    @ApiOperation(value = "用户中心-首页", responseReference = "get")
    public ResponseMessage<IndexInfo> csIndex()  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        return ok(groupCompanyBiz.csIndex(context.getAccountId(), context.getUserId(), context.getParentGroupId()));
    }
}
