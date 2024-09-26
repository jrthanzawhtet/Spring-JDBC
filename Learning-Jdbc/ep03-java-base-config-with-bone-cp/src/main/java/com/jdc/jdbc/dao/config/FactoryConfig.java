package com.jdc.jdbc.dao.config;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

@Configuration
public class FactoryConfig {

	@Bean
	@Qualifier("memberInsert")
	public PreparedStatementCreatorFactory memberInsertCreatorFactory(@Value("${member.insert}") String sql) {
		return new PreparedStatementCreatorFactory(sql,
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, });
	}

	@Bean
	@Qualifier("memberFindByNameLike")
	public PreparedStatementCreatorFactory memberFindByNameLikeCreatorFactory(
			@Value("${member.select.by.name}") String sql) {
		return new PreparedStatementCreatorFactory(sql, new int[] { Types.VARCHAR });
	}

}
