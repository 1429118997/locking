package com.ssm;

import java.lang.annotation.Annotation;

import javax.sql.DataSource;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableTransactionManagement
@ComponentScan(excludeFilters=@ComponentScan.Filter(type=FilterType.ANNOTATION,classes=Controller.class))
public class springConfig {
    
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource ) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
		mapperScanner.setBasePackage("com.ssm.redpacket.mapper");
		mapperScanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScanner.setAnnotationClass(Repository.class);
		return mapperScanner;
	}
	
	
	@Bean
	public RedisTemplate getRedisTemplate() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(10);
		jedisPoolConfig.setMaxIdle(2);
		jedisPoolConfig.setMaxWaitMillis(4000);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		jedisConnectionFactory.afterPropertiesSet();
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		
		RedisTemplate redisTemplate = new RedisTemplate();
		
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		redisTemplate.setDefaultSerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);;
		redisTemplate.setHashValueSerializer(stringRedisSerializer);
		
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		
		return redisTemplate;
	}
	

}
