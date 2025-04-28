/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.postgresql;

import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.dialect.PostgresqlDatabaseDialect;
import com.sinhy.nacos.plugin.datasource.impl.base.BaseConfigTagsRelationMapper;

/**
 * The postgresql implementation of ConfigTagsRelationMapper.
 *
 * @author lilinhai
 **/

public class ConfigTagsRelationMapperByPostgresql extends BaseConfigTagsRelationMapper
{
    
    /**
     * <pre>构造方法</pre>
     * @since 2025-04-27 10:11
     */
    public ConfigTagsRelationMapperByPostgresql()
    {
        super(new PostgresqlDatabaseDialect());
    }
    
    @Override
    public String getDataSource()
    {
        return DatabaseTypeConstant.POSTGRESQL;
    }
    
}
