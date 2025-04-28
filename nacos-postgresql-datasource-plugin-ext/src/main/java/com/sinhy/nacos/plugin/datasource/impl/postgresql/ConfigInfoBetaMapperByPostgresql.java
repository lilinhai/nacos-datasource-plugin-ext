/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.postgresql;

import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.dialect.PostgresqlDatabaseDialect;
import com.sinhy.nacos.plugin.datasource.impl.base.BaseConfigInfoBetaMapper;

/**
 * The postgresql implementation of ConfigInfoBetaMapper.
 *
 * @author lilinhai
 **/

public class ConfigInfoBetaMapperByPostgresql extends BaseConfigInfoBetaMapper
{
    
    /**
     * <pre>构造方法</pre>
     * @since 2025-04-27 10:11
     */
    public ConfigInfoBetaMapperByPostgresql()
    {
        super(new PostgresqlDatabaseDialect());
    }
    
    @Override
    public String getDataSource()
    {
        return DatabaseTypeConstant.POSTGRESQL;
    }
    
}
