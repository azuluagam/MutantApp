package com.ml.mutant.config;

import org.springframework.beans.factory.annotation.Value;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;

@Configuration
@PropertySource(value={"classpath:application.properties"})
public class JpaConfig {

    @Value("${spring.flyway.db.clean:false}")
    private boolean flywayClean;

    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlAuto;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.dialect}")
    private String dialect;

    @Value("${hibernate.jdbc.batch_size}")
    private String batchSize;

    @Value("${hibernate.jdbc.order_inserts}")
    private Boolean orderInserts;

    @Value("${hibernate.jdbc.order_updates}")
    private Boolean orderUpdates;

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            if (flywayClean) {
                flyway.clean();
            }
            flyway.migrate();
        };
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("com.ml.mutant.domain");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.jdbc.batch_size", batchSize);
        properties.put("hibernate.jdbc.order_inserts", orderInserts);
        properties.put("hibernate.jdbc.order_updates", orderUpdates);
        entityManagerFactory.setJpaPropertyMap(properties);
        entityManagerFactory.setPersistenceUnitName("ml.mutant");
        return entityManagerFactory;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl(url);
        poolProperties.setUsername(username);
        poolProperties.setPassword(password);
        poolProperties.setDriverClassName(driverClassName);
        poolProperties.setTestOnBorrow(true);
        poolProperties.setValidationQuery("SELECT 1");
        return new DataSource(poolProperties);
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
