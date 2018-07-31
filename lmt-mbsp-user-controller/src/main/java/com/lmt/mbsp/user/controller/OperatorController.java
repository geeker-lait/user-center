package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.UserBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.UserQuery;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.service.UserService;
import com.lmt.mbsp.user.vo.admin.ToEditAdminInfo;
import com.lmt.mbsp.user.vo.operator.AddOperatorInfo;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.operator.ToEditOperatorInfo;
import com.lmt.mbsp.user.vo.person.EditUserInfo;
import com.lmt.mbsp.user.vo.person.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * 描述:企业用户模块-操作员
 * 作者:Tangsm
 */
@RestController
@RequestMapping("/operator")
public class OperatorController implements CrudController<User,Long,UserQuery,UserInfo> {
    private UserService userService;
    private UserBiz userBiz;

    @Autowired
    public void CrudController(UserService userService, UserBiz userBiz){
        this.userService = userService;
        this.userBiz = userBiz;
    }

    @Override
    public CrudService<User, Long> getService() {
        return userService;
    }

    /****************************************************************************************** 用户中心-企业用户 ******************************************************************/

    @GetMapping("/toSupplement")
    @ApiOperation(value = "用户中心-进入完善/编辑个人资料页面", responseReference = "get")
    public  ResponseMessage<ToEditAdminInfo> toSupplement() throws Exception{
        LoginContext context = LoginContext.getLoginContext();

        return ResponseMessage.ok(userBiz.toSupplement(context.getUserId()));
    }

    @PutMapping("/supplement")
    @ApiOperation(value = "用户中心-完善/编辑个人资料", responseReference = "put")
    public  ResponseMessage<EditUserInfo> supplement(EditUserInfo info) throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());
        info.setId(context.getUserId());

        userBiz.supplement(info);

        return ResponseMessage.ok();
    }

    @PostMapping("/add")
    @ApiOperation(value = "用户中心-新增操作员", responseReference = "post")
    public  ResponseMessage<AddOperatorInfo> add(AddOperatorInfo info) throws Exception {
        LoginContext context = LoginContext.getLoginContext();
        info.setGroupId(context.getGroup().getGroupId());

        userBiz.addOperator(info);

        return ResponseMessage.ok(info);
    }

    @GetMapping("/list")
    @ApiOperation(value = "用户中心-操作员列表", responseReference = "get")
    public  ResponseMessage<List<OperatorListInfo>> list()  throws Exception{
        return ResponseMessage.ok(userBiz.operatorList(LoginContext.getLoginContext().getGroup().getGroupId()));
    }

    @GetMapping("/toEdit/{userId}/{accountId}")
    @ApiOperation(value = "用户中心-进入编辑操作员页面", responseReference = "get")
    public  ResponseMessage<ToEditOperatorInfo> toEdit(@PathVariable Long userId, Long accountId)  throws Exception{
        return ResponseMessage.ok(userBiz.toEditOperator(userId, accountId));
    }

    @PutMapping("/disable/{userId}")
    @ApiOperation(value = "用户中心-禁用操作员", responseReference = "put")
    public  ResponseMessage<Long> disable(@PathVariable Long userId)  throws Exception{
        userBiz.disableUser(userId);
        return ok(userId);
    }

    @PutMapping("/unDisable/{userId}")
    @ApiOperation(value = "用户中心-启用操作员", responseReference = "put")
    public  ResponseMessage<Long> unDisable(@PathVariable Long userId)  throws Exception{
        userBiz.unDisableUser(userId);
        return ok(userId);
    }
}
