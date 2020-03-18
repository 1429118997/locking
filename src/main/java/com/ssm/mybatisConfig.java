package com.ssm;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@org.springframework.context.annotation.Configuration
public class mybatisConfig {

	
	@Bean(name="dataSource",destroyMethod="close")
	public BasicDataSource basicDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
		dataSource.setPassword("lizhihai");
		dataSource.setUsername("root");
		
		dataSource.setMaxIdle(5);
		dataSource.setMaxActive(50);
		dataSource.setDefaultAutoCommit(false);
		return dataSource;
	}
	
	
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		Configuration configuration = new Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		SqlSessionFactory factory=null;
		
		try {
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:com/ssm/redpacket/mapper/xml/*.xml");
			sqlSessionFactoryBean.setConfiguration(configuration);
			sqlSessionFactoryBean.setMapperLocations(resources);
			sqlSessionFactoryBean.setTypeAliasesPackage("com.ssm.redpacket.bean");
			factory=sqlSessionFactoryBean.getObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factory;
		
	}
}
