/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.HistoryConfigInfoMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的BaseHistoryConfigInfoMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 10:19
 * @version V1.0
 */
public abstract class BaseHistoryConfigInfoMapper extends AbstractDataSourceMapper implements HistoryConfigInfoMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 10:19
     * @param databaseDialect
     */
    public BaseHistoryConfigInfoMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
    @Override
    public MapperResult removeConfigHistory(MapperContext context)
    {
        String sql = "DELETE FROM his_config_info WHERE gmt_modified < ?";
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.START_TIME)));
    }
    
    @Override
    public MapperResult pageFindConfigHistoryFetchRows(MapperContext context)
    {
        String sql = getLimitPageSqlWithOffset("SELECT nid,data_id,group_id,tenant_id,app_name,src_ip,src_user,op_type,ext_info,publish_type,gray_name,gmt_create,gmt_modified "
                + "FROM his_config_info " + "WHERE data_id = ? AND group_id = ? AND tenant_id = ? ORDER BY nid DESC ", context.getStartRow(), context.getPageSize());
        return new MapperResult(sql,
                CollectionUtils.list(context.getWhereParameter(FieldConstant.DATA_ID), context.getWhereParameter(FieldConstant.GROUP_ID), context.getWhereParameter(FieldConstant.TENANT_ID)));
    }
    
    @Override
    public String getDataSource()
    {
        return DatabaseTypeConstant.POSTGRESQL;
    }
    
}
