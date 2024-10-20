package com.jdc.demo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.demo.PropagationService;

@SpringJUnitConfig(locations = "classpath:/application.xml")
@Sql(statements = {
		"set foreign_key_checks = 0;",
		"truncate table details_tbl;",
		"truncate table header_tbl",
		"set foreign_key_checks = 1;"

})
public class PropagationTest {
	
	@Autowired
	private PropagationService service;
	
	@ParameterizedTest
	@CsvSource({
		"Header,Details,0,1,1"
	})
	void test(String header, String details, int status,int headerId, int detailsId) {
		var result = service.save(status, header, details);
		assertNotNull(result);
		assertEquals(headerId, result.headerId());
		
		assertNotNull(result.detailsId());
		assertEquals(detailsId, result.detailsId().get(0));
	}

}
