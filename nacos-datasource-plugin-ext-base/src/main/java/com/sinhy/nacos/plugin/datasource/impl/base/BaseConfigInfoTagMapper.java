/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import java.util.Collections;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoTagMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的BaseConfigInfoTagMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 07:44
 * @version V1.0
 */
public abstract class BaseConfigInfoTagMapper extends AbstractDataSourceMapper implements ConfigInfoTagMapper
{
    
    /**
     * <pre>构造方法</pre>
     * @since 2025-04-27 10:09
     * @param databaseDialect
     */
    protected BaseConfigInfoTagMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
    @Override
    public String getTableName()
    {
        return TableConstant.CONFIG_INFO_TAG;
    }
    
    @Override
    public MapperResult findAllConfigInfoTagForDumpAllFetchRows(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String innerSql = databaseDialect.getLimitPageSqlWithOffset("SELECT id FROM config_info_tag  ORDER BY id ", startRow, pageSize);
        String sql = " SELECT t.id,data_id,group_id,tenant_id,tag_id,app_name,content,md5,gmt_modified " + " FROM (  " + innerSql + "  ) " + "g, config_info_tag t  WHERE g.id = t.id  ";
        return new MapperResult(sql, Collections.emptyList());
    }
    
}
