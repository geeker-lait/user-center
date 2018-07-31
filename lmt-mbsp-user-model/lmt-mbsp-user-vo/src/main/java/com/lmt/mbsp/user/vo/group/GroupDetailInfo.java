package com.lmt.mbsp.user.vo.group;

import lombok.Data;

import java.util.List;

/*
 * 描述：内部公司详细页所需展示参数
 * 作者：Tangsm
 * 创建时间：2018-07-24 13:46:13
 */
@Data
public class GroupDetailInfo {
    private GroupInfo groupInfo;    // 当前公司信息
    private List<GroupInfo> parentInfos;   // 父级公司信息
}
