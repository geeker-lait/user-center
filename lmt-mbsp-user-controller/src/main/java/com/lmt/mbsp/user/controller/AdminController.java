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
import com.lmt.mbsp.user.vo.admin.AddAdminInfo;
import com.lmt.mbsp.user.vo.admin.EditAdminInfo;
import com.lmt.mbsp.user.vo.admin.ToEditAdminInfo;
import com.lmt.mbsp.user.vo.person.UserDetailInfo;
import com.lmt.mbsp.user.vo.person.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * Auther: Tangsm
 * Tel:15801818092
 * Date: 7/23/2018 13:53
 * Description:系统用户
 */
@RestController
@RequestMapping("/admin")
public class AdminController implements CrudController<User,Long,UserQuery,UserInfo> {
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
    @GetMapping("/list")
    @ApiOperation(value = "后台-系统用户列表", responseReference = "get")
    public  ResponseMessage<PagerResult<UserInfo>> sysUserList(UserQuery info)  throws Exception{
        return ok(userBiz.sysUserList(info));
    }

    @GetMapping("/detail/{userId}")
    @ApiOperation(value = "后台-系统用户详细", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> detail(@PathVariable Long userId)  throws Exception{
        return ok(userBiz.userDetail(userId, 1));
    }

    @PostMapping("/add")
    @ApiOperation(value = "后台-新增系统用户", responseReference = "post")
    public  ResponseMessage<Long> add(@RequestBody AddAdminInfo info) throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        info.setGroupId(context.getGroup().getGroupId());

        return ok(userBiz.addSysUser(info));
    }

    @GetMapping("/toEdit/{userId}")
    @ApiOperation(value = "后台-进入编辑系统用户页面", responseReference = "get")
    public  ResponseMessage<ToEditAdminInfo> toEdit(@PathVariable Long userId) throws Exception{
        return ok(userBiz.toEditSysUser(userId));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "后台-编辑系统用户", responseReference = "put")
    public  ResponseMessage<EditAdminInfo> edit(@RequestBody EditAdminInfo info) throws Exception{
        userBiz.editSysUser(info);

        return ok(info);
    }

    @GetMapping("/disable/{userId}")
    @ApiOperation(value = "后台-禁用系统用户", responseReference = "get")
    public  ResponseMessage disable(@PathVariable Long userId)  throws Exception{
        userBiz.disableUser(userId);
        return ok();
    }

    @GetMapping("/unDisable/{userId}")
    @ApiOperation(value = "后台-启用系统用户", responseReference = "get")
    public  ResponseMessage unDisable(@PathVariable Long userId)  throws Exception{
        userBiz.unDisableUser(userId);
        return ok();
    }
}
