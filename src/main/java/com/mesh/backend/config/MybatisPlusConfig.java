package com.mesh.backend.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.mesh.backend.entity");
        sqlSessionFactory.setMapperLocations(new
                PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        sqlSessionFactory.setConfiguration(new MybatisConfiguration());
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(false);
        sqlSessionFactory.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactory.getObject();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return i->i.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }
}
