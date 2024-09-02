package com.jdc.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jdc.jdbc.dto.Member;

public class MemberDao {
	
	private JdbcTemplate template;

	public MemberDao(JdbcTemplate template) {
		super();
		this.template = template;
	}
	
	public void create(Member member) {
		template.update("insert into MEMBER (loginId, password, name, phone, email) values (?,?,?,?,?)", 
				member.getLoginId(),
				member.getPassword(),
				member.getName(),
				member.getPhone(),
				member.getEmail());
	}

}
