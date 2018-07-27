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

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/23/2018 13:53
 * @Description:
 */
@RestController
@RequestMapping("/sys-user")
public class SystemUserController implements CrudController<User,Long,UserQuery,UserInfo> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBiz userBiz;

    @Override
    public CrudService<User, Long> getService() {
        return userService;
    }

    /****************************************************************************************** 后台-系统用户 ******************************************************************/
    @GetMapping("list")
    @ApiOperation(value = "后台-系统用户列表", responseReference = "get")
    public ResponseMessage<PagerResult<UserInfo>> sysUserList(UserQuery info)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setTyp(1);
        info.setGroupId(context.getGroup().getId());

        ResponseMessage<PagerResult<UserInfo>> responseMessage = ok(userBiz.sysUserList(info));
        return responseMessage;
    }

    @GetMapping("detail/{id}")
    @ApiOperation(value = "后台-系统用户详细", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> sysUserDetail(@PathVariable Long id)  throws Exception{
        return ok(userBiz.userDetail(id));
    }

    @PostMapping("add")
    @ApiOperation(value = "后台-新增系统用户", responseReference = "post")
    public  ResponseMessage<AddSysUserInfo> addSysUser(@RequestBody AddSysUserInfo info) throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setGroupId(context.getGroup().getId());

        userBiz.addSysUser(info);

        return ok(info);
    }

    @GetMapping("edit/{id}")
    @ApiOperation(value = "后台-查询编辑系统用户信息", responseReference = "get")
    public  ResponseMessage<ToEditSysUserInfo> toEditSysUser(@PathVariable Long id) throws Exception{
        //return ok(userBiz.getEditUser(id));
        return null;
    }

    @PutMapping("edit")
    @ApiOperation(value = "后台-编辑系统用户", responseReference = "put")
    public  ResponseMessage<EditSysUserInfo> editSysUser(@RequestBody EditSysUserInfo info) throws Exception{
        userBiz.editSysUser(info);

        return ok(info);
    }

    @GetMapping("disable/{id}")
    @ApiOperation(value = "后台-禁用系统用户", responseReference = "get")
    public  ResponseMessage disableSysUser(@PathVariable Long id)  throws Exception{
        userBiz.disableUser(id);
        return ok();
    }

    @GetMapping("undisable/{id}")
    @ApiOperation(value = "后台-启用系统用户", responseReference = "get")
    public  ResponseMessage unDisableSysUser(@PathVariable Long id)  throws Exception{
        userBiz.unDisableUser(id);
        return ok();
    }

    @GetMapping("authorize/{id}")
    @ApiOperation(value = "后台-获取系统用户授权信息", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> getSysUserAuthorize(@PathVariable Long id)  throws Exception{
        return ok(userBiz.getUserAuthorize(id));
    }

    @PutMapping("authorize")
    @ApiOperation(value = "后台-系统用户授权", responseReference = "put")
    public  ResponseMessage sysUserAuthorize(@RequestBody SaveUserAuthorizeInfo info)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setAccountId(context.getAccountId());

        userBiz.userAuthorize(info);

        return ok();
    }

    /****************************************************************************************** 后台-个人用户 ******************************************************************/
    @PutMapping("/cms/person/list")
    @ApiOperation(value = "后台-个人用户列表", responseReference = "put")
    public  ResponseMessage<PagerResult<UserInfo>> personUserList(@RequestBody UserQuery info)  throws Exception{
        return ok(userBiz.personUserList(info));
    }

    @GetMapping("/cms/person/add2Group/{id}")
    @ApiOperation(value = "后台-将个人用户加入内部公司", responseReference = "get")
    public  ResponseMessage add2Group(@PathVariable Long id)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();

        userBiz.add2Group(context.getGroup().getId(), id);

        return ok();
    }

    @GetMapping("/cms/person/detail/{id}")
    @ApiOperation(value = "后台-个人用户详细", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> personUserDetail(@PathVariable Long id)  throws Exception{
        return ok(userBiz.userDetail(id));
    }

    @GetMapping("/cms/person/disable/{id}")
    @ApiOperation(value = "后台-禁用个人用户", responseReference = "get")
    public  ResponseMessage disablePersonUser(@PathVariable Long id)  throws Exception{
        userBiz.disableUser(id);
        return ok();
    }

    @GetMapping("/cms/person/unDisable/{id}")
    @ApiOperation(value = "后台-启用个人用户", responseReference = "get")
    public  ResponseMessage unDisablePersonUser(@PathVariable Long id)  throws Exception{
        userBiz.unDisableUser(id);
        return ok();
    }


}
