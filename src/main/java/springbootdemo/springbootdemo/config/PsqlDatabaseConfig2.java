package springbootdemo.springbootdemo.config;

import com.zaxxer.hikari.HikariDataSource;
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
import springbootdemo.springbootdemo.customer.Customer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "psqlEntityManagerFactory",
        transactionManagerRef = "psqlTransactionManager",
        basePackages = {
                "springbootdemo.springbootdemo.customer"
        }
)
public class PsqlDatabaseConfig2 {

    @Primary
    @Bean(name = "psqlProperties")
    @ConfigurationProperties("spring.datasource.psql")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "psqlDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.psql")
    public DataSource dataSource(@Qualifier("psqlProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }


    @Primary
    @Bean(name = "psqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("psqlDatasource") DataSource dataSource
    ){
        return builder.dataSource(dataSource)
                .packages(Customer.class)
                .build();
//                .packages("springbootdemo.springbootdemo.customer.Customer")
//                .persistenceUnit("customer")
//                .build();

    }

    @Primary
    @Bean("psqlTransactionManager")
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager transactionManager(
            @Qualifier("psqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory
            ){
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

}
