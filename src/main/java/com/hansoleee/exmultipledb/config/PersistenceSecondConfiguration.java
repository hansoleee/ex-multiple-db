package com.hansoleee.exmultipledb.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Slf4j
@Configuration
@PropertySource({"classpath:application.yml"})
@EnableJpaRepositories(
        basePackages = "com.hansoleee.exmultipledb.team.repository",
        entityManagerFactoryRef = "secondEntityManager",
        transactionManagerRef = "secondTransactionManager"
)
@RequiredArgsConstructor
public class PersistenceSecondConfiguration {

    private final Environment env;
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    @Bean
    public DataSource secondDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.second-datasource.driver-class-name")));
        dataSource.setJdbcUrl(env.getProperty("spring.second-datasource.url"));
        dataSource.setUsername(env.getProperty("spring.second-datasource.username"));
        dataSource.setPassword(env.getProperty("spring.second-datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManager(EntityManagerFactoryBuilder builder) {
        var properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(secondDataSource())
                .properties(properties)
                .packages("com.hansoleee.exmultipledb.team.domain")
                .persistenceUnit("team")
                .build();
    }

    @Bean
    public PlatformTransactionManager secondTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(secondEntityManager(builder).getObject()));
    }
}