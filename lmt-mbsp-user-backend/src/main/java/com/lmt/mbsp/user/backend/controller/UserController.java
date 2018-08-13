package com.lmt.mbsp.user.backend.controller;

import com.lmt.framework.support.entity.PagerResult;
import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.web.controller.CrudController;
import com.lmt.framework.support.web.controller.QueryController;
import com.lmt.mbsp.user.backend.biz.UserBiz;
import com.lmt.mbsp.user.backend.biz.vo.LoginContext;
import com.lmt.mbsp.user.dto.GroupUserQuery;
import com.lmt.mbsp.user.dto.UserQuery;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.service.UserService;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.user.EditUserInfo;
import com.lmt.mbsp.user.vo.user.UserDetailInfo;
import com.lmt.mbsp.user.vo.user.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * Auther: Tangsm
 * Date: 7/23/2018 13:53
 * Description:用户
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

    /****************************************************************************************** 后台-用户列表 ******************************************************************/
    @GetMapping("/groupUserList")
    @ApiOperation(value = "后台-获取某个组下用户列表（含账号/角色信息）", responseReference = "get")
    public  ResponseMessage<PagerResult<OperatorListInfo>> groupUserList(GroupUserQuery info)  throws Exception{
        return ok(userBiz.groupUserList(info));
    }

    @GetMapping("/list")
    @ApiOperation(value = "后台-用户列表", responseReference = "get")
    public  ResponseMessage<PagerResult<UserInfo>> userList(UserQuery info)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();
        if (info.getGroupId() == null || info.getGroupId().equals(0L)){
            info.setGroupId(context.getGroupId());
        }
        // 超级管理员可以查看所有用户信息
        if (context.getManagerType().equals(1)){
            info.setGroupId(null);
        }

        return ok(userBiz.userList(info));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "后台-用户详细", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> detail(@PathVariable Long id)  throws Exception{
        return ok(userBiz.userDetail(id));
    }

    @GetMapping("/toEdit/{id}")
    @ApiOperation(value = "后台-进入编辑用户页面", responseReference = "get")
    public  ResponseMessage<EditUserInfo> toEdit(@PathVariable Long id) throws Exception{
        return ok(userBiz.toEditUser(id));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "后台-编辑用户", responseReference = "put")
    public  ResponseMessage<EditUserInfo> edit(@RequestBody EditUserInfo info) throws Exception{
        userBiz.editUser(info);

        return ok(info);
    }

    @PutMapping("/add2Group/{userId}")
    @ApiOperation(value = "后台-将个人用户加入内部公司", responseReference = "put")
    public  ResponseMessage<Long> add2Group(@PathVariable Long userId)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();

        userBiz.add2Group(userId, context.getGroupId());

        return ok(userId);
    }

    @PutMapping("/disable/{id}")
    @ApiOperation(value = "后台-禁用用户", responseReference = "put")
    public  ResponseMessage disable(@PathVariable Long id)  throws Exception{
        userBiz.disableUser(id);
        return ok();
    }

    @PutMapping("/unDisable/{id}")
    @ApiOperation(value = "后台-激活用户", responseReference = "put")
    public  ResponseMessage unDisable(@PathVariable Long id)  throws Exception{
        userBiz.unDisableUser(id);
        return ok();
    }
}
