package com.example.ms_backend.configs;

import com.example.ms_backend.entities.users.Role;
import com.example.ms_backend.entities.users.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.ms_backend.entities.users",
        entityManagerFactoryRef = "usersEntityManagerFactory",
        transactionManagerRef = "usersTransactionManager"
)
public class UserDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.users")
    public DataSourceProperties usersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource usersDataSource(DataSourceProperties usersDataSourceProperties) {
        return usersDataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(
            DataSource usersDataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(usersDataSource)
                .packages(UserEntity.class, Role.class)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager usersTransactionManager(
            LocalContainerEntityManagerFactoryBean usersEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(usersEntityManagerFactory.getObject()));
    }

}
