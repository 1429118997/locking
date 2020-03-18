package com.ssm;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class applicationConfig  extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return  new Class[] {springConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {springmvcConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		String path="classpath:file";
		long maxFileSize=(long) (5*Math.pow(2, 20));
		long maxRequestSize=(long) (10*Math.pow(2, 20));
		registration.setMultipartConfig(new MultipartConfigElement(path, maxFileSize, maxRequestSize, 0));
	}
	
	

}
