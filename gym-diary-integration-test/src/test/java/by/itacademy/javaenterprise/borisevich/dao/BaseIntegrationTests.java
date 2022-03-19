package by.itacademy.javaenterprise.borisevich.dao;

import by.itacademy.javaenterprise.borisevich.config.PersistanceConfig;
import by.itacademy.javaenterprise.borisevich.config.TestConfiguration;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {PersistanceConfig.class, TestConfiguration.class})
public class BaseIntegrationTests {
    static DataSource ds;
    static MySQLContainer<?> mysqlOldVersion;

    @BeforeAll
    public static void beforeClass() throws Exception {
        Properties props = new Properties();

        try (InputStream in = BaseIntegrationTests.class.getClassLoader().getResourceAsStream("test.properties")) {
            props.load(in);
        } catch (IOException e) {
            log.info("Error with reading file liquibase.properties.", e);
        }
        mysqlOldVersion = new MySQLContainer<>(DockerImageName.parse(props.getProperty("docker.imagename")))
                .withDatabaseName(props.getProperty("database.name"))
                .withUsername(props.getProperty("username"))
                .withPassword(props.getProperty("password"));
        mysqlOldVersion.start();

        ds = new DriverManagerDataSource(mysqlOldVersion.getJdbcUrl(),
                mysqlOldVersion.getUsername(), mysqlOldVersion.getPassword());
        JdbcConnection jdbcConnection = new JdbcConnection(ds.getConnection());
        Database database = DatabaseFactory.getInstance().
                findCorrectDatabaseImplementation(jdbcConnection);
        Liquibase liquibase = new liquibase.Liquibase("liquibase/db/changelog/changelog.xml"
                , new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts());
    }

    @DynamicPropertySource
    public static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlOldVersion::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlOldVersion::getUsername);
        registry.add("spring.datasource.password", mysqlOldVersion::getPassword);
        registry.add("datasource.driverClassName", mysqlOldVersion::getDriverClassName);
    }

}
