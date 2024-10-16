package com.jdc.trx.demo;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {
		"com.jdc.trx.demo.model"
})
@EnableTransactionManagement
public class ApplicationConfig {
	
	@Bean
	DataSource dataSource() {
		var bean = new BasicDataSource();
		bean.setUrl("jdbc:mysql://localhost:3306/transfer_db");
		bean.setUsername("root");
		bean.setPassword("your_password");
		return bean;
	}
	
	@Bean
	TransactionManager transferManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
