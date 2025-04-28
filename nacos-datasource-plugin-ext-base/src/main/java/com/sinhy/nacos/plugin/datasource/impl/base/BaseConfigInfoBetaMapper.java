/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import java.util.Collections;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoBetaMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的BaseConfigInfoBetaMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 07:42
 * @version V1.0
 */
public abstract class BaseConfigInfoBetaMapper extends AbstractDataSourceMapper implements ConfigInfoBetaMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 10:09
     * @param databaseDialect
     */
    protected BaseConfigInfoBetaMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
    @Override
    public String getTableName()
    {
        return TableConstant.CONFIG_INFO_BETA;
    }
    
    public String getLimitPageSqlWithOffset(String sql, int startRow, int pageSize)
    {
        return databaseDialect.getLimitPageSqlWithOffset(sql, startRow, pageSize);
    }
    
    @Override
    public MapperResult findAllConfigInfoBetaForDumpAllFetchRows(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sqlInner = getLimitPageSqlWithOffset("SELECT id FROM config_info_beta  ORDER BY id ", startRow, pageSize);
        String sql = " SELECT t.id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,beta_ips,encrypted_data_key " + " FROM ( " + sqlInner + "  )"
                + "  g, config_info_beta t WHERE g.id = t.id ";
        return new MapperResult(sql, Collections.emptyList());
    }
    
}
