package by.itacademy.javaenterprise.borisevich.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class TestConfiguration {
    @Primary
    @Bean
    public DataSource dataSource(@Value("${datasource.driverClassName}") String driver,
                                     @Value("${spring.datasource.url}") String url,
                                     @Value("${spring.datasource.username}") String userName,
                                     @Value("${spring.datasource.password}") String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }
}
