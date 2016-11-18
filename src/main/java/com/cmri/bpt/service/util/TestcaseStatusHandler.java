package com.cmri.bpt.service.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.cmri.bpt.entity.testcase.TestcaseLog.TestcaseStatus;

public class TestcaseStatusHandler extends BaseTypeHandler<TestcaseStatus> {

	private Class<TestcaseStatus> type;

	private final TestcaseStatus[] enums;

	public TestcaseStatusHandler(Class<TestcaseStatus> type) {

		if (type == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null)
			throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, TestcaseStatus parameter, JdbcType jdbcType)
			throws SQLException {

		ps.setString(i, parameter.getText());

	}

	@Override
	public TestcaseStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {

		String i = rs.getString(columnName);

		if (rs.wasNull()) {
			return null;
		} else {

			return createStatus(i);
		}
	}

	@Override
	public TestcaseStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

		String i = rs.getString(columnIndex);

		if (rs.wasNull()) {
			return null;
		} else {

			return createStatus(i);
		}
	}

	@Override
	public TestcaseStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String i = cs.getString(columnIndex);

		if (cs.wasNull()) {
			return null;
		} else {

			return createStatus(i);
		}
	}

	private TestcaseStatus createStatus(String code) {
		for (TestcaseStatus status : enums) {
			if (status.getText().equals(code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("未知的枚举类型：" + code + ",请核对" + type.getSimpleName());
	}

}
