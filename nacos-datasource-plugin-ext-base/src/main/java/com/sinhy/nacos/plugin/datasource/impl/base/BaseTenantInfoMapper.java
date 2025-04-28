/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import com.alibaba.nacos.plugin.datasource.mapper.TenantInfoMapper;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的BaseTenantInfoMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 09:20
 * @version V1.0
 */
public abstract class BaseTenantInfoMapper extends AbstractDataSourceMapper implements TenantInfoMapper
{
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 10:10
     * @param databaseDialect
     */
    protected BaseTenantInfoMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
}
