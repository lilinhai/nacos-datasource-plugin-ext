/*
 * Copyright 1999-2023 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinhy.nacos.plugin.datasource.impl.oracle;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.nacos.common.utils.NamespaceUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigTagsRelationMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.dialect.OracleDatabaseDialect;

/***
 * @author lilinhai
 */
public class ConfigTagsRelationMapperByOracle extends AbstractOracleMapper implements ConfigTagsRelationMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 11:33
     * @param databaseDialect
     */
    public ConfigTagsRelationMapperByOracle()
    {
        super(new OracleDatabaseDialect());
    }
    
    @Override
    public MapperResult findConfigInfo4PageCountRows(MapperContext context)
    {
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);
        
        List<Object> paramList = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sqlCount = "SELECT count(*) FROM config_info  a LEFT JOIN config_tags_relation b ON a.id=b.id";
        
        where.append(" a.tenant_id=NVL(?,'").append(NamespaceUtil.getNamespaceDefaultId()).append("') ");
        
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
        }
        where.append(") ");
        return new MapperResult(sqlCount + where, paramList);
    }
    
    @Override
    public MapperResult findConfigInfoLike4PageCountRows(MapperContext context)
    {
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String tenantId = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);
        
        List<Object> paramList = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sqlCount = "SELECT count(*) FROM config_info  a LEFT JOIN config_tags_relation b ON a.id=b.id ";
        
        if (StringUtils.isBlank(tenantId))
        {
            where.append(" a.tenant_id ='").append(NamespaceUtil.getNamespaceDefaultId()).append("' ");
        }
        else
        {
            where.append(" a.tenant_id LIKE ? ");
            paramList.add(tenantId);
        }
        
        if (!StringUtils.isBlank(dataId))
        {
            where.append(" AND a.data_id LIKE ? ");
            paramList.add(dataId);
        }
        if (StringUtils.isNotBlank(group))
        {
            where.append(" AND a.group_id LIKE ? ");
            paramList.add(group);
        }
        if (StringUtils.isNotBlank(appName))
        {
            where.append(" AND a.app_name = ? ");
            paramList.add(appName);
        }
        if (StringUtils.isNotBlank(content))
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
        return new MapperResult(sqlCount + where, paramList);
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
        final String sql = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content FROM config_info  a LEFT JOIN " + "config_tags_relation b ON a.id=b.id";
        
        where.append(" a.tenant_id=NVL(?, '").append(NamespaceUtil.getNamespaceDefaultId()).append("') ");
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
        List<Object> paramList = new ArrayList<>();
        StringBuilder where = new StringBuilder(" WHERE ");
        final String sqlFetchRows = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content " + "FROM config_info a LEFT JOIN config_tags_relation b ON a.id=b.id ";
        
        if (StringUtils.isBlank(tenant))
        {
            where.append(" a.tenant_id = '").append(NamespaceUtil.getNamespaceDefaultId()).append("' ");
        }
        else
        {
            where.append(" a.tenant_id LIKE ? ");
            paramList.add(tenant);
        }
        
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
        String sql = getLimitPageSqlWithOffset(sqlFetchRows + where, startRow, pageSize);
        return new MapperResult(sql, paramList);
    }
    
}
