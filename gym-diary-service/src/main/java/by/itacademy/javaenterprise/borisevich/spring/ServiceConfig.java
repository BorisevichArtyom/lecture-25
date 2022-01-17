package by.itacademy.javaenterprise.borisevich.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "by.itacademy.javaenterprise.borisevich.services")
public class ServiceConfig {
}
