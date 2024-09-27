package com.jdc.jdbc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jdc.jdbc.dao.meta.MapRow;
import com.jdc.jdbc.dto.Member;

@MapRow
public class MemberRowMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		var m = new Member();
		m.setLoginId(rs.getString(1));
		m.setPassword(rs.getString(2));
		m.setName(rs.getString(3));
		m.setPhone(rs.getString(4));
		m.setEmail(rs.getString(5));
		return m;
	}

}
