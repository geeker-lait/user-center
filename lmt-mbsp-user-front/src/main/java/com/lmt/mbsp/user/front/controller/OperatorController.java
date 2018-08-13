package com.lmt.mbsp.user.front.controller;

import com.lmt.framework.support.model.message.ResponseMessage;
import com.lmt.mbsp.user.front.biz.LoginBiz;
import com.lmt.mbsp.user.front.biz.UserBiz;
import com.lmt.mbsp.user.front.biz.vo.LoginContext;
import com.lmt.mbsp.user.vo.operator.AddOperatorInfo;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.user.EditUserInfo;
import com.lmt.mbsp.user.vo.user.UserDetailInfo;
import com.lmt.mbsp.user.vo.user.UserInfo;
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
//public class OperatorController implements CrudController<User,Long,UserQuery,UserInfo> {
public class OperatorController{
    private UserBiz userBiz;
    private LoginBiz loginBiz;

    @Autowired
    public OperatorController(UserBiz userBiz, LoginBiz loginBiz){
        this.userBiz = userBiz;
        this.loginBiz = loginBiz;
    }

    /****************************************************************************************** 用户中心-企业用户 ******************************************************************/

    @GetMapping("/detail")
    @ApiOperation(value = "用户中心-个人资料详细页", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> detail() throws Exception{
        LoginContext context = loginBiz.getLoginContext();

        return ok(userBiz.userDetail(context.getUserId()));
    }

    @GetMapping("/toEdit")
    @ApiOperation(value = "用户中心-进入编辑个人资料页面", responseReference = "get")
    public  ResponseMessage<UserInfo> toEdit() throws Exception{
        LoginContext context = loginBiz.getLoginContext();

        return ResponseMessage.ok(userBiz.toEdit(context.getUserId()));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "用户中心-更新个人资料", responseReference = "put")
    public  ResponseMessage<EditUserInfo> supplement(EditUserInfo info) throws Exception{
        LoginContext context = loginBiz.getLoginContext();
        info.setAccountId(context.getAccountId());
        info.setId(context.getUserId());

        userBiz.edit(info);

        return ResponseMessage.ok();
    }

    @GetMapping("/operatorDetail/{userId}")
    @ApiOperation(value = "用户中心-操作员详细页", responseReference = "get")
    public  ResponseMessage<UserDetailInfo> detail(@PathVariable Long userId) throws Exception{
        return ok(userBiz.userDetail(userId));
    }

    @PostMapping("/add")
    @ApiOperation(value = "用户中心-新增操作员", responseReference = "post")
    public  ResponseMessage<AddOperatorInfo> add(AddOperatorInfo info) throws Exception {
        LoginContext context = loginBiz.getLoginContext();
        info.setGroupId(context.getGroupId());

        userBiz.addOperator(info);

        return ResponseMessage.ok(info);
    }

    @GetMapping("/list")
    @ApiOperation(value = "用户中心-操作员列表", responseReference = "get")
    public  ResponseMessage<List<OperatorListInfo>> list()  throws Exception{
        return ResponseMessage.ok(userBiz.operatorList(loginBiz.getLoginContext().getGroupId()));
    }

    @PutMapping("/disable/{id}")
    @ApiOperation(value = "用户中心-禁用操作员", responseReference = "put")
    public  ResponseMessage<Long> disable(@PathVariable Long id)  throws Exception{
        userBiz.disableUser(id);
        return ok(id);
    }

    @PutMapping("/unDisable/{id}")
    @ApiOperation(value = "用户中心-激活操作员", responseReference = "put")
    public  ResponseMessage<Long> unDisable(@PathVariable Long id)  throws Exception{
        userBiz.unDisableUser(id);
        return ok(id);
    }
}
