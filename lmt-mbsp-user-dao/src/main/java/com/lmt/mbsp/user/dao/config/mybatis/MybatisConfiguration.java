package com.lmt.mbsp.user.dao.config.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @描述: mybatis配置.
 * @作者: lijing.
 * @创建时间: 2018-06-27 10:58.
 * @版本: 1.0 .
 */
@Configuration
@MapperScan(basePackages = "**.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);
    @Autowired
    DataSource dataSource;


    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(PageInterceptor pageInterceptor) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setPlugins(new Interceptor[] {pageInterceptor});

        return bean.getObject();
    }



    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager rdsTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "pageInterceptor")
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("helperDialect", "mysql");
        pageInterceptor.setProperties(p);
        return pageInterceptor;
    }



}
