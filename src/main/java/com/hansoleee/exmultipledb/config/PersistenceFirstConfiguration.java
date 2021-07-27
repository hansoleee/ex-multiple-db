package com.hansoleee.exmultipledb.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@PropertySource({"classpath:application.yml"})
@EnableJpaRepositories(
        basePackages = "com.hansoleee.exmultipledb.member.repository",
        entityManagerFactoryRef = "firstEntityManager",
        transactionManagerRef = "firstTransactionManager"
)
@RequiredArgsConstructor
public class PersistenceFirstConfiguration {

    private final Environment env;
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    @Primary
    @Bean
    public DataSource firstDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.first-datasource.driver-class-name")));
        dataSource.setJdbcUrl(env.getProperty("spring.first-datasource.url"));
        dataSource.setUsername(env.getProperty("spring.first-datasource.username"));
        dataSource.setPassword(env.getProperty("spring.first-datasource.password"));
        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean firstEntityManager(EntityManagerFactoryBuilder builder) {
        var properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(firstDataSource())
                .properties(properties)
                .packages("com.hansoleee.exmultipledb.member.domain")
                .persistenceUnit("member")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager firstTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(firstEntityManager(builder).getObject()));
    }
}