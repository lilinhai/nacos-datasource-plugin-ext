/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import java.util.Collections;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoGrayMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象BaseConfigInfoGrayMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 07:41
 * @version V1.0
 */
public abstract class BaseConfigInfoGrayMapper extends AbstractDataSourceMapper implements ConfigInfoGrayMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 10:03
     * @param databaseDialect
     */
    protected BaseConfigInfoGrayMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
    @Override
    public String getTableName()
    {
        return TableConstant.CONFIG_INFO_GRAY;
    }
    
    @Override
    public MapperResult findAllConfigInfoGrayForDumpAllFetchRows(MapperContext context)
    {
        String sql = " SELECT id,data_id,group_id,tenant_id,gray_name,gray_rule,app_name,content,md5,gmt_modified " + " FROM  config_info_gray  ORDER BY id ";
        sql = databaseDialect.getLimitPageSqlWithOffset(sql, context.getStartRow(), context.getPageSize());
        return new MapperResult(sql, Collections.emptyList());
    }
    
}
