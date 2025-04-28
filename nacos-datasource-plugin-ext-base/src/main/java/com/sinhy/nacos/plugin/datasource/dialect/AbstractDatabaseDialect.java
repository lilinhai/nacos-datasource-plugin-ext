/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.dialect;

import com.sinhy.nacos.plugin.datasource.constants.PrimaryKeyConstant;

/**
 * 抽象数据源方言定义
 * @author sinhy
 * @since 2025-04-28 13:10
 * @version V1.0
 */
public abstract class AbstractDatabaseDialect implements DatabaseDialect
{
    
    @Override
    public int getPagePrevNum(int page, int pageSize)
    {
        return (page - 1) * pageSize;
    }
    
    @Override
    public int getPageLastNum(int page, int pageSize)
    {
        return pageSize;
    }
    
    @Override
    public String getLimitTopSqlWithMark(String sql)
    {
        return sql + " LIMIT ? ";
    }
    
    @Override
    public String getLimitPageSqlWithMark(String sql)
    {
        return sql + " LIMIT ?,? ";
    }
    
    @Override
    public String getLimitPageSql(String sql, int pageNo, int pageSize)
    {
        return sql + "  LIMIT " + getPagePrevNum(pageNo, pageSize) + " , " + pageSize;
    }
    
    @Override
    public String getLimitPageSqlWithOffset(String sql, int startOffset, int pageSize)
    {
        return sql + "  LIMIT " + startOffset + " , " + pageSize;
    }
    
    @Override
    public String[] getReturnPrimaryKeys()
    {
        return PrimaryKeyConstant.LOWER_RETURN_PRIMARY_KEYS;
    }
    
}
