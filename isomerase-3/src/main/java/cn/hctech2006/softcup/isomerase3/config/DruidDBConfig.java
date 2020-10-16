package cn.hctech2006.softcup.isomerase3.config;

import cn.hctech2006.softcup.isomerase3.dynamic.DynamicDataSource;
import cn.hctech2006.softcup.isomerase3.util.PropertiesUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.DigestUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类, DruidDBConfig
 * 在Ｓｐｒｉｎｇ Boot启动的时候就会实例化，　主要功能是创建数据源对象
 * 和第三步的动态数据元对象，　动态数据源对象手上有一个：袋子：，
 * 　用；来装具体的数据源对象，　通过代码可以看到，　主数据员也在袋子之中
 */
@Configuration
@EnableTransactionManagement
public class DruidDBConfig {
    private final Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);
    //@Value()
    private String dbUrl = PropertiesUtil.getProperty("spring.datasource.one.druid.url");
    //@Value("${spring.datasource.one.druid.username}")
    private String username = PropertiesUtil.getProperty("spring.datasource.one.druid.username");
    //@Value("${spring.datasource.one.druid.password}")
    private String password= PropertiesUtil.getProperty("spring.datasource.one.druid.password");
    //@Value("${spring.datasource.one.druid.driverClassName}")
    private String driverClassName= PropertiesUtil.getProperty("spring.datasource.one.druid.driver-class-name");
    //@Value("${spring.datasource.one.druid.initialSize}")
    private int initialSize= Integer.parseInt(PropertiesUtil.getProperty("spring.datasource.one.druid.initialSize"));
    //@Value("${spring.datasource.one.druid.minIdle}")
    private int minIdle= Integer.parseInt(PropertiesUtil.getProperty("spring.datasource.one.druid.minIdle"));
    @Value("${spring.datasource.one.druid.maxActive}")
    private int maxActive=Integer.parseInt( PropertiesUtil.getProperty("spring.datasource.one.druid.maxActive"));
    @Value("${spring.datasource.one.druid.maxWait}")
    private long maxWait= Long.parseLong(PropertiesUtil.getProperty("spring.datasource.one.druid.maxWait"));
    @Value("${spring.datasource.one.druid.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis= Long.parseLong(PropertiesUtil.getProperty("spring.datasource.one.druid.timeBetweenEvictionRunsMillis"));
    @Value("${spring.datasource.one.druid.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis= Long.parseLong(PropertiesUtil.getProperty("spring.datasource.one.druid.minEvictableIdleTimeMillis"));
    @Value("${spring.datasource.one.druid.vaildationQuery}")
    private String vaildationQuery= PropertiesUtil.getProperty("spring.datasource.one.druid.vaildationQuery");
    @Value("${spring.datasource.one.druid.testWhileIdle}")
    private String testWhileIdle = PropertiesUtil.getProperty("spring.datasource.one.druid.testWhileIdle");
    @Value("${spring.datasource.one.druid.testOnBorrow}")
    private boolean testOnBorrow= Boolean.getBoolean(PropertiesUtil.getProperty("spring.datasource.one.druid.testOnBorrow"));
    @Value("${spring.datasource.one.druid.testOnReturn}")
    private boolean testOnReturn= Boolean.getBoolean(PropertiesUtil.getProperty("spring.datasource.one.druid.testOnReturn"));
    @Value("${spring.datasource.one.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements= Boolean.getBoolean(PropertiesUtil.getProperty("spring.datasource.one.druid.poolPreparedStatements"));
    @Value("${spring.datasource.one.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize= Integer.parseInt(PropertiesUtil.getProperty("spring.datasource.one.druid.maxPoolPreparedStatementPerConnectionSize"));
    @Value("${spring.datasource.one.druid.filters}")
    private String filters= PropertiesUtil.getProperty("spring.datasource.one.druid.filters");
    @Bean
    @Primary
    @Qualifier("adiDataSource")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(this.dbUrl);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(this.initialSize);
        dataSource.setMinIdle(this.minIdle);
        dataSource.setMaxActive(this.maxActive);
        dataSource.setMaxWait(this.maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(vaildationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            dataSource.setFilters(filters);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return dataSource;
    }
    @Bean
    @Qualifier("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDebug(false);
        dynamicDataSource.setDefaultTargetDataSource(dataSource());
        Map<Object, Object> targetDataSources = new HashMap<>();
        String datasourceId = DigestUtils.md5DigestAsHex("adiDataSource".getBytes());
        targetDataSources.put(datasourceId, dataSource());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }
    @Bean(name = "adiJdbcTemplate")
    @Qualifier("adiJdbcTemplate")
    public NamedParameterJdbcTemplate adiJdbcTemplate(
            @Qualifier("adiDataSource") DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
    @Bean(name = "dynamicJdbcTemplate")
    @Qualifier("dynamicJdbcTemplate")
    public NamedParameterJdbcTemplate dynamicJdbcTemplate(
            @Qualifier("dynamicDataSource") DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
