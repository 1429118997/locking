package com.ssm;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.TaskExecutorFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(includeFilters=@ComponentScan.Filter(type=FilterType.ANNOTATION,classes=Controller.class))
public class springmvcConfig extends AsyncConfigurerSupport{

	@Bean
	public InternalResourceViewResolver initViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}
	
	
	@Bean
	public MultipartResolver initMultipartResolver() {
		return new StandardServletMultipartResolver();
	}
    
	
	@Bean
	public HandlerAdapter initRequestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
		ArrayList<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(mediaType);
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
		
		requestMappingHandlerAdapter.getMessageConverters().add(mappingJackson2HttpMessageConverter);
		return requestMappingHandlerAdapter;
	}
	
	@Override
	@Bean
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(10);
		threadPoolTaskExecutor.setQueueCapacity(200);
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
		
	}
	
	
}
