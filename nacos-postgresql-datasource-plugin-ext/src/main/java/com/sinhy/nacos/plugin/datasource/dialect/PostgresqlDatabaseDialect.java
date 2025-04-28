/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.dialect;

import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;

/**
 * PostgreSQL database dialect.
 * 
 * @author lilinhai
 */
public class PostgresqlDatabaseDialect extends AbstractDatabaseDialect
{
    
    @Override
    public String getType()
    {
        return DatabaseTypeConstant.POSTGRESQL;
    }
    
    @Override
    public String getLimitTopSqlWithMark(String sql)
    {
        return sql + " LIMIT ? ";
    }
    
    @Override
    public String getLimitPageSqlWithMark(String sql)
    {
        return sql + "  OFFSET ? LIMIT ? ";
    }
    
    @Override
    public String getLimitPageSql(String sql, int pageNo, int pageSize)
    {
        return sql + "  OFFSET " + getPagePrevNum(pageNo, pageSize) + " LIMIT " + pageSize;
    }
    
    @Override
    public String getLimitPageSqlWithOffset(String sql, int startOffset, int pageSize)
    {
        return sql + "  OFFSET " + startOffset + " LIMIT " + pageSize;
    }
    
}
