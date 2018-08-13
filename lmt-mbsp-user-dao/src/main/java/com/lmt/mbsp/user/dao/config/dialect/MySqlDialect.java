package com.lmt.mbsp.user.dao.config.dialect;

/**
 * @描述: MySql Dialect.
 * @作者: lijing.
 * @创建时间: 2018-06-28 10:48.
 * @版本: 1.0 .
 */
public class MySqlDialect extends Dialect {
    public boolean supportsLimitOffset() {
        return true;
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {

        if (offset > 0) {
            sql += " limit " + offsetPlaceholder + "," + limitPlaceholder;
        } else {
            sql += " limit " + limitPlaceholder;
        }

        return sql;
    }
}
