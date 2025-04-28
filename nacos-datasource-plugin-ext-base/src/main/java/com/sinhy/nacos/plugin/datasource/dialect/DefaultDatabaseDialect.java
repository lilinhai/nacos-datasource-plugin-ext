/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.dialect;

import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;

/**
 * 默认数据库方言实现
 * @author sinhy
 * @since 2025-04-28 13:11
 * @version V1.0
 */
public class DefaultDatabaseDialect extends AbstractDatabaseDialect
{
    
    @Override
    public String getType()
    {
        return DatabaseTypeConstant.POSTGRESQL;
    }
    
}
