package ru.shestakova.autoconfigure;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EntityScan(basePackages = "ru.shestakova.model")
@EnableJpaRepositories(basePackages = "ru.shestakova.repository")
@EnableTransactionManagement @EnableJpaAuditing
@AutoConfigureBefore(DataSource.class)
public class ModelAutoConfiguration {

}
