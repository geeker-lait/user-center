package com.lmt.framework.support.bean;

import com.lmt.framework.support.entity.Entity;
import com.lmt.framework.utils.bean.BeanUtils;
import com.lmt.mbsp.user.dto.AccountQuery;
import com.lmt.mbsp.user.entity.account.Account;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * 描述：
 * 作者：Tangsm
 * 创建时间：2018-07-27 17:03:29
 */
public class EntityToModelUtils {

    public static void main(String[] args) {
        Account a = new Account();
        a.setId(11L);

        Account b = new Account();
        b.setId(22L);

        List<Account> accounts = new ArrayList<>();
        accounts.add(a);
        accounts.add(b);
        try {
            System.out.println(entitysToInfos(accounts, AccountQuery.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将entity集合转为info集合
     * @param entitys   源（entity对象集合）
     * @param c 转换结果对象
     * @param <V>
     * @return
     */
    public static <V> List<V> entitysToInfos(List<?> entitys, Class<V> c) throws Exception {
        List<V> list = new ArrayList<>();
        if (entitys != null && entitys.size() > 0){
            Iterator it = entitys.iterator();

            while(true) {
                Object obj;
                do {
                    if (!it.hasNext()) {
                        return list;
                    }

                    obj = it.next();
                } while(obj == null);

                list.add(entityToInfo((Entity)obj, c.newInstance()));
            }
        }

        return list;
    }

    /**
     * 将entity转为info
     * @param entity   源（entity对象）
     * @param v 转换结果对象
     * @param <V>
     * @return
     */
    public static <V> V entityToInfo(Entity entity, V v){
        BeanUtils.copyProperties(entity, v);

        return v;
    }
}
