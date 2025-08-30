/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.NamespaceUtil;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.GroupCapacityMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的BaseGroupCapacityMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 07:45
 * @version V1.0
 */
public abstract class BaseGroupCapacityMapper extends AbstractDataSourceMapper implements GroupCapacityMapper
{
    
    /**
     * <pre>构造方法</pre>
     * @since 2025-04-27 10:09
     * @param databaseDialect
     */
    protected BaseGroupCapacityMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
    @Override
    public MapperResult selectGroupInfoBySize(MapperContext context)
    {
        String sql = databaseDialect.getLimitTopSqlWithMark("SELECT id, group_id FROM group_capacity WHERE id > ?");
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.ID), context.getPageSize()));
    }
    
    @Override
    public MapperResult select(MapperContext context)
    {
        String sql = "SELECT id, quota, usage, max_size, max_aggr_count, max_aggr_size, group_id FROM group_capacity " + "WHERE group_id = ?";
        return new MapperResult(sql, Collections.singletonList(context.getWhereParameter(FieldConstant.GROUP_ID)));
    }
    
    @Override
    public MapperResult insertIntoSelect(MapperContext context)
    {
        List<Object> paramList = new ArrayList<>();
        paramList.add(context.getUpdateParameter(FieldConstant.GROUP_ID));
        paramList.add(context.getUpdateParameter(FieldConstant.QUOTA));
        paramList.add(context.getUpdateParameter(FieldConstant.MAX_SIZE));
        paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_COUNT));
        paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_SIZE));
        paramList.add(context.getUpdateParameter(FieldConstant.GMT_CREATE));
        paramList.add(context.getUpdateParameter(FieldConstant.GMT_MODIFIED));
        
        String sql = "INSERT INTO group_capacity (group_id, quota, usage, max_size, max_aggr_count, max_aggr_size,gmt_create," + " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info";
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult insertIntoSelectByWhere(MapperContext context)
    {
        final String sql = "INSERT INTO group_capacity (group_id, quota, usage, max_size, max_aggr_count, max_aggr_size, gmt_create,"
                + " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info WHERE group_id=? AND tenant_id = '" + NamespaceUtil.getNamespaceDefaultId() + "'";
        List<Object> paramList = new ArrayList<>();
        paramList.add(context.getUpdateParameter(FieldConstant.GROUP_ID));
        paramList.add(context.getUpdateParameter(FieldConstant.QUOTA));
        paramList.add(context.getUpdateParameter(FieldConstant.MAX_SIZE));
        paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_COUNT));
        paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_SIZE));
        paramList.add(context.getUpdateParameter(FieldConstant.GMT_CREATE));
        paramList.add(context.getUpdateParameter(FieldConstant.GMT_MODIFIED));
        
        paramList.add(context.getWhereParameter(FieldConstant.GROUP_ID));
        
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult incrementUsageByWhereQuotaEqualZero(MapperContext context)
    {
        return new MapperResult("UPDATE group_capacity SET usage = usage + 1, gmt_modified = ? WHERE group_id = ? AND usage < ? AND quota = 0",
                CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED), context.getWhereParameter(FieldConstant.GROUP_ID), context.getWhereParameter(FieldConstant.USAGE)));
    }
    
    @Override
    public MapperResult incrementUsageByWhereQuotaNotEqualZero(MapperContext context)
    {
        return new MapperResult("UPDATE group_capacity SET usage = usage + 1, gmt_modified = ? WHERE group_id = ? AND usage < quota AND quota != 0",
                CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED), context.getWhereParameter(FieldConstant.GROUP_ID)));
    }
    
    @Override
    public MapperResult incrementUsageByWhere(MapperContext context)
    {
        return new MapperResult("UPDATE group_capacity SET usage = usage + 1, gmt_modified = ? WHERE group_id = ?",
                CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED), context.getWhereParameter(FieldConstant.GROUP_ID)));
    }
    
    @Override
    public MapperResult decrementUsageByWhere(MapperContext context)
    {
        return new MapperResult("UPDATE group_capacity SET usage = usage - 1, gmt_modified = ? WHERE group_id = ? AND usage > 0",
                CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED), context.getWhereParameter(FieldConstant.GROUP_ID)));
    }
    
    @Override
    public MapperResult updateUsage(MapperContext context)
    {
        return new MapperResult("UPDATE group_capacity SET usage = (SELECT count(*) FROM config_info), gmt_modified = ? WHERE group_id = ?",
                CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED), context.getWhereParameter(FieldConstant.GROUP_ID)));
    }
    
    @Override
    public MapperResult updateUsageByWhere(MapperContext context)
    {
        return new MapperResult(
                "UPDATE group_capacity SET usage = (SELECT count(*) FROM config_info WHERE group_id=? AND tenant_id = '" + NamespaceUtil.getNamespaceDefaultId() + "'),"
                        + " gmt_modified = ? WHERE group_id= ?",
                CollectionUtils.list(context.getWhereParameter(FieldConstant.GROUP_ID), context.getUpdateParameter(FieldConstant.GMT_MODIFIED), context.getWhereParameter(FieldConstant.GROUP_ID)));
    }
}
