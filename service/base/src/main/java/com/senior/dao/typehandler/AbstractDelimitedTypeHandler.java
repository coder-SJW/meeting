
package com.senior.dao.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.google.common.base.Splitter;

/**
 * @author senior
 */
public abstract class AbstractDelimitedTypeHandler<T> extends BaseTypeHandler<List<T>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType)
            throws SQLException {
        List<String> strings = parameter.stream().map(String::valueOf).collect(Collectors.toList());
        ps.setString(i, String.join(",", strings));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getNullableResult(rs.getString(columnName));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getNullableResult(rs.getString(columnIndex));
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getNullableResult(cs.getString(columnIndex));
    }

    @SuppressWarnings("squid:S1168")
    private List<T> getNullableResult(String str) {
        if (str == null) {
            // 数据库字段为null，向上层返回null，而不是空集合
            return null;
        } else {
            List<String> strings = Splitter.on(',').omitEmptyStrings().splitToList(str);
            return strings.stream().map(this::convert).collect(Collectors.toCollection(ArrayList::new));
        }
    }

    protected abstract T convert(String s);
}
