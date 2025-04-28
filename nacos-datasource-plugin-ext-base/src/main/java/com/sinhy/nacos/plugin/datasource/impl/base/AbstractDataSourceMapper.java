/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */
package com.sinhy.nacos.plugin.datasource.impl.base;

import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;
import com.sinhy.nacos.plugin.datasource.enumer.TrustedSqlFunctionEnum;

/**
 * 抽象的数据源映射器
 * 
 * @author lilinhai
 * @since 2025-04-27 07:30
 * @version V1.0
 */
public abstract class AbstractDataSourceMapper extends AbstractMapper
{
    
    protected final DatabaseDialect databaseDialect;
    
    protected AbstractDataSourceMapper(DatabaseDialect databaseDialect)
    {
        this.databaseDialect = databaseDialect;
    }
    
    protected String getLimitPageSqlWithOffset(String sql, int startOffset, int pageSize)
    {
        return databaseDialect.getLimitPageSqlWithOffset(sql, startOffset, pageSize);
    }
    
    protected String getLimitPageSqlWithMark(String sql)
    {
        return databaseDialect.getLimitPageSqlWithMark(sql);
    }
    
    public String getFunction(String functionName)
    {
        return TrustedSqlFunctionEnum.getFunctionByName(functionName);
    }
}
