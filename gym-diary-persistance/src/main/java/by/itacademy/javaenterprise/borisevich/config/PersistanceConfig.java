package by.itacademy.javaenterprise.borisevich.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Properties;
@Configuration
@ComponentScan(basePackages = "by.itacademy.javaenterprise.borisevich.dao")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class PersistanceConfig {
    @Autowired
    private Environment environment;
    @Autowired
    private HikariConfig hikariConfig;

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hconfig = new HikariConfig();
        hconfig.setDriverClassName(environment.getProperty("datasource.driverClassName"));
        hconfig.setJdbcUrl(environment.getProperty("datasource.jdbcUrl"));
        hconfig.setUsername(environment.getProperty("dataSource.user"));
        hconfig.setPassword(environment.getProperty("dataSource.password"));
        hconfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("dataSource.maxPoolSize")));
        hconfig.addDataSourceProperty("cachePrepStmts", environment.getProperty("dataSource.cachePrepStmts"));
        hconfig.addDataSourceProperty("prepStmtCacheSize", environment.getProperty("dataSource.prepStmtCacheSize"));
        hconfig.addDataSourceProperty("prepStmtCacheSqlLimit", environment.getProperty("dataSource.prepStmtCacheSqlLimit"));
        return hconfig;
    }

    @Bean
    public HikariDataSource dataSource() {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("by.itacademy.javaenterprise.borisevich.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(hibernateProperties());
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    protected TransactionTemplate transactionTemplate() {
        var transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager());
        transactionTemplate.setTimeout(10000);
        return transactionTemplate;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

}
