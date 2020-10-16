package cn.hctech2006.softcup.datasource.datasource;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 实现Spring Bean生命周期接口ApplicationContextAware
 */
@Component
public class DataSourceShow implements ApplicationContextAware {
    ApplicationContext applicationContext = null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println("-----------------------------------------------");
        System.out.println("数据库连接池类型: "+dataSource.getClass().getName());
        System.out.println("-----------------------------------------------");

    }
}
