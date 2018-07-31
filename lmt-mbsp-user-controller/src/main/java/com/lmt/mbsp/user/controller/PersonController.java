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
import com.lmt.mbsp.user.vo.person.UserDetailInfo;
import com.lmt.mbsp.user.vo.person.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static com.lmt.framework.support.model.message.ResponseMessage.ok;

/**
 * 描述:个人用户模块
 * 作者:Tangsm
 */
@RestController
@RequestMapping("/person")
public class PersonController implements CrudController<User,Long,UserQuery,UserInfo> {
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

    /****************************************************************************************** 后台-个人用户 ******************************************************************/
    @GetMapping("/list")
    @ApiOperation(value = "后台-个人用户列表", responseReference = "get")
    public  ResponseMessage<PagerResult<UserInfo>> personUserList(UserQuery info)  throws Exception{
       return ok(userBiz.personUserList(info));
    }

    @PostMapping("/add2Group/{userId}")
    @ApiOperation(value = "后台-将个人用户加入内部公司", responseReference = "post")
    public  ResponseMessage<Long> add2Group(@PathVariable Long userId)  throws Exception{
        LoginContext context = LoginContext.getLoginContext();

        userBiz.add2Group(context.getGroup().getGroupId(), userId);

        return ok(userId);
    }

    @GetMapping("/detail/{userId}")
    @ApiOperation(value = "后台-个人用户详细", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> personUserDetail(@PathVariable Long userId)  throws Exception{
        return ok(userBiz.userDetail(userId, 0));
    }

    @PutMapping("/disable/{userId}")
    @ApiOperation(value = "后台-禁用个人用户", responseReference = "put")
    public  ResponseMessage disablePersonUser(@PathVariable Long userId)  throws Exception{
        userBiz.disableUser(userId);
        return ok();
    }

    @PutMapping("/unDisable/{userId}")
    @ApiOperation(value = "后台-启用个人用户", responseReference = "put")
    public  ResponseMessage unDisablePersonUser(@PathVariable Long userId)  throws Exception{
        userBiz.unDisableUser(userId);
        return ok();
    }
}
