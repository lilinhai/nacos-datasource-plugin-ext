/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */
package com.sinhy.nacos.plugin.datasource.impl.base;

import com.alibaba.nacos.plugin.datasource.mapper.ConfigMigrateMapper;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的BaseConfigMigrateMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 10:28
 * @version V1.0
 */
public abstract class BaseConfigMigrateMapper extends AbstractDataSourceMapper implements ConfigMigrateMapper
{
    
    /**
     * <pre>构造方法</pre>
     * @since 2025-04-27 10:28
     * @param databaseDialect
     */
    protected BaseConfigMigrateMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
}
