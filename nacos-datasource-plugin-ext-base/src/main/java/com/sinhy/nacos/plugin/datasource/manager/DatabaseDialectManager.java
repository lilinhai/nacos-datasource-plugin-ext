/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.manager;

import com.alibaba.nacos.common.spi.NacosServiceLoader;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;
import com.sinhy.nacos.plugin.datasource.dialect.DefaultDatabaseDialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DatabaseDialect SPI Manager.
 * 
 * @author lilinhai
 */
public class DatabaseDialectManager
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseDialectManager.class);
    
    private static final DatabaseDialectManager INSTANCE = new DatabaseDialectManager();
    
    private static final Map<String, DatabaseDialect> SUPPORT_DIALECT_MAP = new ConcurrentHashMap<String, DatabaseDialect>();
    
    private DatabaseDialectManager()
    {
    }
    
    static
    {
        // 加载多种数据库方言为映射信息
        Collection<DatabaseDialect> dialectList = NacosServiceLoader.load(DatabaseDialect.class);
        
        for (DatabaseDialect dialect : dialectList)
        {
            SUPPORT_DIALECT_MAP.put(dialect.getType(), dialect);
        }
        if (SUPPORT_DIALECT_MAP.isEmpty())
        {
            LOGGER.warn("[DatasourceDialectManager] Load DatabaseDialect fail, No DatabaseDialect implements");
        }
    }
    
    public DatabaseDialect getDialect(String databaseType)
    {
        DatabaseDialect databaseDialect = SUPPORT_DIALECT_MAP.get(databaseType);
        if (databaseDialect == null)
        {
            return new DefaultDatabaseDialect();
        }
        return databaseDialect;
    }
    
    /**
     * Get DatasourceDialectManager instance.
     *
     * @return DataSourceDialectProvider
     */
    public static DatabaseDialectManager getInstance()
    {
        return INSTANCE;
    }
    
}
