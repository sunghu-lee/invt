package com.naviworks.invt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.naviworks.invt.filter.CORSFilter;

@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class InvtApplication {
	
	@Configuration
	public class WebConfig implements WebMvcConfigurer
	{
	    @Bean
	    public FilterRegistrationBean getFilterRegistrationBean()
	    {
	        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CORSFilter());
	        registrationBean.addUrlPatterns("/Search");
	        registrationBean.addUrlPatterns("/Search/*");
	        return registrationBean;
	    }
	}

	public static void main(String[] args) {
		SpringApplication.run(InvtApplication.class, args);
	}

}
