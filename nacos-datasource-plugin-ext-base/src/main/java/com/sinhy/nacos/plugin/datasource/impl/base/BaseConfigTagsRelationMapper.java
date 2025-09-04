/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.nacos.common.utils.ArrayUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigTagsRelationMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的BaseConfigTagsRelationMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 07:45
 * @version V1.0
 */
public abstract class BaseConfigTagsRelationMapper extends AbstractDataSourceMapper implements ConfigTagsRelationMapper
{
    
    /**
     * <pre>构造方法</pre>
     * @since 2025-04-27 10:09
     * @param databaseDialect
     */
    protected BaseConfigTagsRelationMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
    public String getLimitPageSqlWithOffset(String sql, int startOffset, int pageSize)
    {
        return databaseDialect.getLimitPageSqlWithOffset(sql, startOffset, pageSize);
    }
    
    @Override
    public String getTableName()
    {
        return TableConstant.CONFIG_TAGS_RELATION;
    }
    
    @Override
    public MapperResult findConfigInfo4PageFetchRows(MapperContext context)
    {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);
        List<Object> paramList = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sql = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content FROM config_info  a LEFT JOIN config_tags_relation b ON a.id=b.id";
        
        where.append(" a.tenant_id=? ");
        paramList.add(tenant);
        if (StringUtils.isNotBlank(dataId))
        {
            where.append(" AND a.data_id=? ");
            paramList.add(dataId);
        }
        if (StringUtils.isNotBlank(group))
        {
            where.append(" AND a.group_id=? ");
            paramList.add(group);
        }
        if (StringUtils.isNotBlank(appName))
        {
            where.append(" AND a.app_name=? ");
            paramList.add(appName);
        }
        if (!StringUtils.isBlank(content))
        {
            where.append(" AND a.content LIKE ? ");
            paramList.add(content);
        }
        
        where.append(" AND b.tag_name IN (");
        for (int i = 0; i < tagArr.length; i++)
        {
            if (i != 0)
            {
                where.append(", ");
            }
            where.append('?');
            paramList.add(tagArr[i]);
        }
        where.append(") ");
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String resultSql = getLimitPageSqlWithOffset(sql + where, startRow, pageSize);
        return new MapperResult(resultSql, paramList);
    }
    
    @Override
    public MapperResult findConfigInfoLike4PageFetchRows(MapperContext context)
    {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);
        final String[] types = (String[]) context.getWhereParameter(FieldConstant.TYPE);
        List<Object> paramList = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sqlFetchRows = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content,a.type FROM config_info a LEFT JOIN config_tags_relation b ON a.id=b.id ";
        
        where.append(" a.tenant_id LIKE ? ");
        paramList.add(tenant);
        if (!StringUtils.isBlank(dataId))
        {
            where.append(" AND a.data_id LIKE ? ");
            paramList.add(dataId);
        }
        if (!StringUtils.isBlank(group))
        {
            where.append(" AND a.group_id LIKE ? ");
            paramList.add(group);
        }
        if (!StringUtils.isBlank(appName))
        {
            where.append(" AND a.app_name = ? ");
            paramList.add(appName);
        }
        if (!StringUtils.isBlank(content))
        {
            where.append(" AND a.content LIKE ? ");
            paramList.add(content);
        }
        
        where.append(" AND (");
        for (int i = 0; i < tagArr.length; i++)
        {
            if (i != 0)
            {
                where.append(" OR ");
            }
            
            where.append(" b.tag_name like ? ");
            paramList.add(tagArr[i]);
        }
        where.append(") ");
        
        if (!ArrayUtils.isEmpty(types))
        {
            where.append(" AND b.type IN (");
            for (int i = 0; i < types.length; i++)
            {
                if (i != 0)
                {
                    where.append(", ");
                }
                where.append('?');
                paramList.add(types[i]);
            }
            where.append(") ");
        }
        
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sql = getLimitPageSqlWithOffset(sqlFetchRows + where, startRow, pageSize);
        return new MapperResult(sql, paramList);
    }
    
}
