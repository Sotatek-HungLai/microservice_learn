package com.example.ms_backend.configs;

import com.example.ms_backend.entities.products.ProductEntity;
import com.example.ms_backend.entities.products.ProductUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.example.ms_backend.entities.products",
        entityManagerFactoryRef = "productsEntityManagerFactory",
        transactionManagerRef = "productsTransactionManager"
)
public class ProductDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.products")
    public DataSourceProperties productsDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean
    public DataSource productsDataSource(DataSourceProperties productsDataSourceProperties ) {
        return  productsDataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean productsEntityManagerFactory(
            DataSource productsDataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(productsDataSource)
                .packages(ProductEntity.class, ProductUser.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager productsTransactionManager(
            LocalContainerEntityManagerFactoryBean productsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(productsEntityManagerFactory.getObject()));
    }
}

