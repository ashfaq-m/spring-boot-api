package springbootdemo.springbootdemo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "psqlEntityMangerFactoryBean",
        basePackages = {
                "springbootdemo.springbootdemo.customer"
        },
        transactionManagerRef = "psqlTransactionManager"
)
public class PsqlDataBaseConfig {


    @Autowired
    private Environment environment;

    // Configuring the data source for the psql database
    @Bean(name = "psqlDataSource")
    //@Primary
    public DataSource dataSource(){

        DriverManagerDataSource managerDataSource = new DriverManagerDataSource();
        managerDataSource.setUrl(environment.getProperty("spring.datasource.psql.url"));
        //managerDataSource.setDriverClassName(environment.getProperty("spring.datasource.psql.driver-class-name"));
        managerDataSource.setUsername(environment.getProperty("spring.datasource.psql.username"));
        managerDataSource.setPassword(environment.getProperty("spring.datasource.psql.password"));


        return managerDataSource;
    }

    // Configuring the entity manager factory for the psql database
    @Bean(name = "psqlEntityMangerFactoryBean")
    //@Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("springbootdemo.springbootdemo.customer");

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        Map<String,String> props = new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","update");

        factoryBean.setJpaPropertyMap(props);

        return factoryBean;
    }

    // Configuring the platform transaction manager for the psql database
    @Bean(name = "psqlTransactionManager")
    @Primary
    public PlatformTransactionManager platformTransactionManager(){
        JpaTransactionManager jpaTransactionManager =
                new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return jpaTransactionManager;
    }
}
