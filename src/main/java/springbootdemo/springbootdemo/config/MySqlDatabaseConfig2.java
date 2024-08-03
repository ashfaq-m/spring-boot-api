package springbootdemo.springbootdemo.config;

import com.zaxxer.hikari.HikariDataSource;
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
import springbootdemo.springbootdemo.student.StudentEntity;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager",
        basePackages = {
                "springbootdemo.springbootdemo.student"
        }
)
public class MySqlDatabaseConfig2 {

    //@Primary
    @Bean(name = "mysqlProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }

    //@Primary
    @Bean(name = "mysqlDatasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(@Qualifier("mysqlProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    //@Primary
    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mysqlDatasource") DataSource dataSource
    ){
        return builder.dataSource(dataSource)
                .packages(StudentEntity.class)
                .build();
//                .packages("springbootdemo.springbootdemo.student.StudentEntity")
//                .persistenceUnit("student")
//                .build();
    }

    //@Primary
    @Bean("mysqlTransactionManager")
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager transactionManager(
            @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
