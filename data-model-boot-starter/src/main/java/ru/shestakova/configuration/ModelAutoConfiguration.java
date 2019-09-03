package ru.shestakova.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "ru.shestakova.model")
@EnableJpaRepositories(basePackages = "ru.shestakova.repository")
@EnableTransactionManagement @EnableJpaAuditing
public class ModelAutoConfiguration {

}
