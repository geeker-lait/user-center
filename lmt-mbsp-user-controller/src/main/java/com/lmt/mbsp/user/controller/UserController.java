package com.lmt.mbsp.user.controller;

import com.lmt.framework.support.entity.PagerResult;
import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.mbsp.user.biz.UserBiz;
import com.lmt.mbsp.user.controller.login.LoginContext;
import com.lmt.mbsp.user.dto.UserQuery;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.service.UserService;
import com.lmt.mbsp.user.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * 描述:用户模块
 * 作者:Tangsm
 */
@RestController
@RequestMapping("/user")
public class UserController implements CrudController<User,Long,UserQuery,UserInfo> {
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

    /****************************************************************************************** 后台-系统用户 ******************************************************************/
    @GetMapping("/cms/sysUser/list")
    @ApiOperation(value = "后台-系统用户列表", responseReference = "get")
    public  ResponseMessage<PagerResult<UserInfo>> sysUserList(UserQuery info)  throws Exception{
        return ok(userBiz.sysUserList(info));
    }

    @GetMapping("/cms/sysUser/detail/{id}")
    @ApiOperation(value = "后台-系统用户详细", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> sysUserDetail(@PathVariable Long id)  throws Exception{
        return ok(userBiz.userDetail(id));
    }

    @PostMapping("/cms/sysUser/add")
    @ApiOperation(value = "后台-新增系统用户", responseReference = "post")
    public  ResponseMessage<Long> addSysUser(@RequestBody AddSysUserInfo info) throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setGroupId(context.getGroup().getGroupId());

        return ok(userBiz.addSysUser(info));
    }

    @GetMapping("/cms/sysUser/toEdit/{id}")
    @ApiOperation(value = "后台-进入编辑系统用户页面", responseReference = "get")
    public  ResponseMessage<ToEditSysUserInfo> toEditSysUser(@PathVariable Long id) throws Exception{
        return ok(userBiz.toEditUser(id));
    }

    @PutMapping("/cms/sysUser/edit")
    @ApiOperation(value = "后台-编辑系统用户", responseReference = "put")
    public  ResponseMessage<EditSysUserInfo> editSysUser(@RequestBody EditSysUserInfo info) throws Exception{
        userBiz.editSysUser(info);

        return ok(info);
    }

    @GetMapping("/cms/sysUser/disable/{id}")
    @ApiOperation(value = "后台-禁用系统用户", responseReference = "get")
    public  ResponseMessage disableSysUser(@PathVariable Long id)  throws Exception{
        userBiz.disableUser(id);
        return ok();
    }

    @GetMapping("/cms/sysUser/unDisable/{id}")
    @ApiOperation(value = "后台-启用系统用户", responseReference = "get")
    public  ResponseMessage unDisableSysUser(@PathVariable Long id)  throws Exception{
        userBiz.unDisableUser(id);
        return ok();
    }

    @GetMapping("/cms/sysUser/toAuthorize/{userId}/{accountId}")
    @ApiOperation(value = "后台-进入系统用户授权页面", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> toSysUserAuthorize(@PathVariable Long userId, @PathVariable Long accountId)  throws Exception{
        return ok(userBiz.toSysUserAuthorize(userId, accountId));
    }

    @PutMapping("/cms/sysUser/authorize")
    @ApiOperation(value = "后台-系统用户授权", responseReference = "put")
    public  ResponseMessage sysUserAuthorize(@RequestBody SaveUserAuthorizeInfo info)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());

        userBiz.userAuthorize(info);

        return ok();
    }

    /****************************************************************************************** 后台-个人用户 ******************************************************************/
    @GetMapping("/cms/person/list")
    @ApiOperation(value = "后台-个人用户列表", responseReference = "get")
    public  ResponseMessage<PagerResult<UserInfo>> personUserList(@RequestBody UserQuery info)  throws Exception{
       return ok(userBiz.personUserList(info));
    }

    @PostMapping("/cms/person/add2Group/{id}")
    @ApiOperation(value = "后台-将个人用户加入内部公司", responseReference = "post")
    public  ResponseMessage add2Group(@PathVariable Long id)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();

        userBiz.add2Group(context.getGroup().getGroupId(), id);

        return ok();
    }

    @GetMapping("/cms/person/detail/{id}")
    @ApiOperation(value = "后台-个人用户详细", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> personUserDetail(@PathVariable Long id)  throws Exception{
        return ok(userBiz.userDetail(id));
    }

    @PutMapping("/cms/person/disable/{id}")
    @ApiOperation(value = "后台-禁用个人用户", responseReference = "put")
    public  ResponseMessage disablePersonUser(@PathVariable Long id)  throws Exception{
        userBiz.disableUser(id);
        return ok();
    }

    @PutMapping("/cms/person/unDisable/{id}")
    @ApiOperation(value = "后台-启用个人用户", responseReference = "put")
    public  ResponseMessage unDisablePersonUser(@PathVariable Long id)  throws Exception{
        userBiz.unDisableUser(id);
        return ok();
    }

    @GetMapping("/cms/operator/list/{groupId}")
    @ApiOperation(value = "后台-企业商户操作员列表", responseReference = "get")
    public  ResponseMessage<List<OperatorListInfo>> userAccountList(@PathVariable Long groupId)  throws Exception{
        return ResponseMessage.ok(userBiz.userAccountList(groupId));
    }

    @PutMapping("/cms/operator/addManager/{groupId}/{accountId}")
    @ApiOperation(value = "后台-企业商户授权管理员", responseReference = "put")
    public  ResponseMessage<Long> addManager(@PathVariable Long groupId, @PathVariable Long accountId) throws Exception{
        userBiz.addManager(groupId, accountId);

        return ok(groupId);
    }

    /****************************************************************************************** 用户中心 ******************************************************************/
    @PutMapping("/cs/supplementInfo")
    @ApiOperation(value = "用户中心-完善个人资料", responseReference = "put")
    public  ResponseMessage<UserInfo> supplementInfo(@RequestBody UserInfo info) throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());
        info.setId(context.getUserId());
        info.setUsername(context.getUsername());

        userBiz.supplementInfo(info);

        return ResponseMessage.ok(info);
    }

    @PutMapping("/cs/editInfo")
    @ApiOperation(value = "用户中心-编辑个人资料", responseReference = "put")
    public  ResponseMessage<UserInfo> editInfo(@RequestBody UserInfo info)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());
        info.setId(context.getUserId());
        info.setUsername(context.getUsername());

        userBiz.editInfo(info);

        return ResponseMessage.ok(info);
    }

    @PostMapping("/cs/operator/add")
    @ApiOperation(value = "用户中心-新增操作员", responseReference = "post")
    public  ResponseMessage<AddOperatorInfo> addOperator(@RequestBody AddOperatorInfo info) throws Exception {
        // TODO 登录信息获取
        LoginContext context = LoginContext.getLoginContext();
        info.setGroupId(context.getGroup().getGroupId());

        userBiz.addOperator(info);

        return ResponseMessage.ok(info);
    }

    @PutMapping("/cs/operator/authorize")
    @ApiOperation(value = "用户中心-操作员授权", responseReference = "put")
    public  ResponseMessage editOperator(@RequestBody SaveUserAuthorizeInfo info) throws Exception{
        userBiz.userAuthorize(info);

        return ResponseMessage.ok();
    }

    @GetMapping("/cs/operator/list")
    @ApiOperation(value = "用户中心-操作员列表", responseReference = "get")
    public  ResponseMessage<List<OperatorListInfo>> operatorList()  throws Exception{
        return ResponseMessage.ok(userBiz.operatorList(LoginContext.getLoginContext().getGroup().getGroupId()));
    }

    @GetMapping("/cs/operator/toAuthorize/{userId}/{groupId}")
    @ApiOperation(value = "用户中心-进入操作员授权页面", responseReference = "get")
    public  ResponseMessage toAuthorize(@PathVariable Long userId, @PathVariable Long groupId)  throws Exception{
        return ResponseMessage.ok(userBiz.toSysUserAuthorize(userId, groupId));
    }

    @PutMapping("/cs/operator/disable/{id}")
    @ApiOperation(value = "用户中心-禁用操作员", responseReference = "put")
    public  ResponseMessage disableOperator(@PathVariable Long id)  throws Exception{
        userBiz.disableUser(id);
        return ok();
    }

    @PutMapping("/cs/operator/unDisable/{id}")
    @ApiOperation(value = "用户中心-启用操作员", responseReference = "put")
    public  ResponseMessage unDisableOperator(@PathVariable Long id)  throws Exception{
        userBiz.unDisableUser(id);
        return ok();
    }
}
