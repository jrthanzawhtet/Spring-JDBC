package com.jdc.trx.demo.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.trx.demo.ApplicationConfig;
import com.jdc.trx.demo.model.TransferService;
import com.jdc.trx.demo.model.dto.TransferForm;

@Sql("/initialize.sql")
@SpringJUnitConfig(classes = ApplicationConfig.class)
public class TransferOperationTest {

	@Autowired
	private TransferService service;
	
	@ParameterizedTest
	@CsvSource({
		"1,2,50000,2022-12-10 10:00"
	})
	void test(int from, int to, int amount, String time) {
		service.transfer(new TransferForm(from, amount, amount, LocalDateTime.parse(time,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
	}
}
