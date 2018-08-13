package com.lmt.mbsp.user.dao.config.mybatis;

import com.lmt.mbsp.user.dto.PageQueryEntity;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.apache.ibatis.mapping.BoundSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @描述: 分页组件.
 * @作者: lijing.
 * @创建时间: 2018-06-28 11:02.
 * @版本: 1.0 .
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {
    Logger logger=LoggerFactory.getLogger(PageInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        String id = mappedStatement.getId();
        //匹配在mybatis中定义的与分页有关的查询id 为select 或selectAll时候，则开始分页

        if (id.contains("selectAll")) {
            //BoundSql中有原始的sql语句和对应的查询参数
            BoundSql boundSql = statementHandler.getBoundSql();

            PageQueryEntity page = (PageQueryEntity) boundSql.getParameterObject();
            if(page!=null){
                String sql = boundSql.getSql();
                String countSql = "select count(0) from (" + sql + ")a";
                Connection connection = (Connection) invocation.getArgs()[0];
                PreparedStatement countStatement = connection.prepareStatement(countSql);
                ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
                parameterHandler.setParameters(countStatement);
                ResultSet rs = countStatement.executeQuery();
                if (rs.next()) {
                    //为什么是getInt（1）? 因为数据表的列是从1开始计数
                    page.setTotal(rs.getInt(1));
                }
                if(page.getPageNum()!=null&&page.getPageSize()!=null){
                    int start = (page.getPageNum()-1)*page.getPageSize();
                    int offset = page.getPageSize();
                    String pageSql = sql + " limit " + start + "," + offset;
                    metaObject.setValue("delegate.boundSql.sql", pageSql);
                }

            }

        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
