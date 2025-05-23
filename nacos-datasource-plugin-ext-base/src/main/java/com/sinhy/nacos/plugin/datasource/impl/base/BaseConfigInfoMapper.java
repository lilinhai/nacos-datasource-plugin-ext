/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.impl.base;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.dialect.DatabaseDialect;

/**
 * 抽象的 BaseConfigInfoMapper
 * 
 * @author lilinhai
 * @since 2025-04-27 10:03
 * @version V1.0
 */
public abstract class BaseConfigInfoMapper extends AbstractDataSourceMapper implements ConfigInfoMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 10:03
     * @param databaseDialect
     */
    protected BaseConfigInfoMapper(DatabaseDialect databaseDialect)
    {
        super(databaseDialect);
    }
    
    @Override
    public MapperResult findConfigInfoByAppFetchRows(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String tenantId = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        String sql = getLimitPageSqlWithOffset("SELECT id,data_id,group_id,tenant_id,app_name,content FROM config_info" + " WHERE tenant_id LIKE ? AND app_name= ?", startRow, pageSize);
        return new MapperResult(sql, CollectionUtils.list(tenantId, appName));
    }
    
    @Override
    public MapperResult getTenantIdList(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sql = getLimitPageSqlWithOffset("SELECT tenant_id FROM config_info WHERE tenant_id != '' GROUP BY tenant_id ", startRow, pageSize);
        return new MapperResult(sql, Collections.emptyList());
    }
    
    @Override
    public MapperResult getGroupIdList(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sql = getLimitPageSqlWithOffset("SELECT group_id FROM config_info WHERE tenant_id ='' GROUP BY group_id ", +startRow, pageSize);
        return new MapperResult(sql, Collections.emptyList());
    }
    
    @Override
    public MapperResult findAllConfigKey(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String innerSql = getLimitPageSqlWithOffset(" SELECT id FROM config_info WHERE tenant_id LIKE ? ORDER BY id ", startRow, pageSize);
        String sql = " SELECT data_id,group_id,app_name  FROM ( " + innerSql + " g, config_info t WHERE g.id = t.id  ";
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.TENANT_ID)));
    }
    
    @Override
    public MapperResult findAllConfigInfoBaseFetchRows(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String innerSql = getLimitPageSqlWithMark(" SELECT id FROM config_info ORDER BY id ");
        String sql = " SELECT t.id,data_id,group_id,content,md5" + " FROM ( " + innerSql + "  ) " + " g, config_info t  WHERE g.id = t.id ";
        return new MapperResult(sql, CollectionUtils.list(startRow, pageSize));
    }
    
    @Override
    public MapperResult findAllConfigInfoFragment(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sql = getLimitPageSqlWithOffset("SELECT id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,type,encrypted_data_key " + "FROM config_info WHERE id > ? ORDER BY id ASC ",
                startRow,
                pageSize);
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.ID)));
    }
    
    @Override
    public MapperResult findChangeConfigFetchRows(MapperContext context)
    {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String tenantTmp = StringUtils.isBlank(tenant) ? StringUtils.EMPTY : tenant;
        final Timestamp startTime = (Timestamp) context.getWhereParameter(FieldConstant.START_TIME);
        final Timestamp endTime = (Timestamp) context.getWhereParameter(FieldConstant.END_TIME);
        final long lastMaxId = (long) context.getWhereParameter(FieldConstant.LAST_MAX_ID);
        final int pageSize = context.getPageSize();
        List<Object> paramList = new ArrayList<>();
        
        final String sqlFetchRows = "SELECT id,data_id,group_id,tenant_id,app_name,content,type,md5,gmt_modified FROM config_info WHERE ";
        String where = " 1=1 ";
        if (!StringUtils.isBlank(dataId))
        {
            where += " AND data_id LIKE ? ";
            paramList.add(dataId);
        }
        if (!StringUtils.isBlank(group))
        {
            where += " AND group_id LIKE ? ";
            paramList.add(group);
        }
        if (!StringUtils.isBlank(tenantTmp))
        {
            where += " AND tenant_id = ? ";
            paramList.add(tenantTmp);
        }
        if (!StringUtils.isBlank(appName))
        {
            where += " AND app_name = ? ";
            paramList.add(appName);
        }
        if (startTime != null)
        {
            where += " AND gmt_modified >=? ";
            paramList.add(startTime);
        }
        if (endTime != null)
        {
            where += " AND gmt_modified <=? ";
            paramList.add(endTime);
        }
        String originSql = sqlFetchRows + where + " AND id > " + lastMaxId + " ORDER BY id ASC";
        String sql = getLimitPageSqlWithOffset(originSql, 0, pageSize);
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult listGroupKeyMd5ByPageFetchRows(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String innerSql = getLimitPageSqlWithOffset(" SELECT id FROM config_info ORDER BY id ", startRow, pageSize);
        String sql = " SELECT t.id,data_id,group_id,tenant_id,app_name,md5,type,gmt_modified,encrypted_data_key FROM " + "( " + innerSql + " ) g, config_info t WHERE g.id = t.id";
        return new MapperResult(sql, Collections.emptyList());
    }
    
    @Override
    public MapperResult findConfigInfoBaseLikeFetchRows(MapperContext context)
    {
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String sqlFetchRows = "SELECT id,data_id,group_id,tenant_id,content FROM config_info WHERE ";
        String where = " 1=1 AND tenant_id='' ";
        List<Object> paramList = new ArrayList<>();
        if (!StringUtils.isBlank(dataId))
        {
            where += " AND data_id LIKE ? ";
            paramList.add(dataId);
        }
        if (!StringUtils.isBlank(group))
        {
            where += " AND group_id LIKE ";
            paramList.add(group);
        }
        if (!StringUtils.isBlank(content))
        {
            where += " AND content LIKE ? ";
            paramList.add(content);
        }
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sql = getLimitPageSqlWithOffset(sqlFetchRows + where, startRow, pageSize);
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult findConfigInfo4PageFetchRows(MapperContext context)
    {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        List<Object> paramList = new ArrayList<>();
        final String sql = "SELECT id,data_id,group_id,tenant_id,app_name,content,type,encrypted_data_key FROM config_info";
        StringBuilder where = new StringBuilder(" WHERE ");
        where.append(" tenant_id=? ");
        paramList.add(tenant);
        if (StringUtils.isNotBlank(dataId))
        {
            where.append(" AND data_id=? ");
            paramList.add(dataId);
        }
        if (StringUtils.isNotBlank(group))
        {
            where.append(" AND group_id=? ");
            paramList.add(group);
        }
        if (StringUtils.isNotBlank(appName))
        {
            where.append(" AND app_name=? ");
            paramList.add(appName);
        }
        if (!StringUtils.isBlank(content))
        {
            where.append(" AND content LIKE ? ");
            paramList.add(content);
        }
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String resultSql = getLimitPageSqlWithOffset(sql + where, startRow, pageSize);
        return new MapperResult(resultSql, paramList);
    }
    
    @Override
    public MapperResult findConfigInfoBaseByGroupFetchRows(MapperContext context)
    {
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sql = "SELECT id,data_id,group_id,content FROM config_info WHERE group_id=? AND tenant_id=? ";
        String resultSql = getLimitPageSqlWithOffset(sql, startRow, pageSize);
        return new MapperResult(resultSql, CollectionUtils.list(context.getWhereParameter(FieldConstant.GROUP_ID), context.getWhereParameter(FieldConstant.TENANT_ID)));
    }
    
    @Override
    public MapperResult findConfigInfoLike4PageFetchRows(MapperContext context)
    {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String sqlFetchRows = "SELECT id,data_id,group_id,tenant_id,app_name,content,encrypted_data_key FROM config_info";
        StringBuilder where = new StringBuilder(" WHERE ");
        where.append(" tenant_id LIKE ? ");
        List<Object> paramList = new ArrayList<>();
        paramList.add(tenant);
        if (!StringUtils.isBlank(dataId))
        {
            where.append(" AND data_id LIKE ? ");
            paramList.add(dataId);
        }
        if (!StringUtils.isBlank(group))
        {
            where.append(" AND group_id LIKE ? ");
            paramList.add(group);
        }
        if (!StringUtils.isBlank(appName))
        {
            where.append(" AND app_name = ? ");
            paramList.add(appName);
        }
        if (!StringUtils.isBlank(content))
        {
            where.append(" AND content LIKE ? ");
            paramList.add(content);
        }
        int startRow = context.getStartRow();
        int pageSize = context.getPageSize();
        String sql = getLimitPageSqlWithOffset(sqlFetchRows + where, startRow, pageSize);
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult findAllConfigInfoFetchRows(MapperContext context)
    {
        String innerSql = getLimitPageSqlWithMark("SELECT id FROM config_info WHERE tenant_id LIKE ? ORDER BY id ");
        String sql = " SELECT t.id,data_id,group_id,tenant_id,app_name,content,md5 " + " FROM ( " + innerSql + " )" + " g, config_info t  WHERE g.id = t.id ";
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.TENANT_ID), context.getStartRow(), context.getPageSize()));
    }
    
    @Override
    public String getTableName()
    {
        return TableConstant.CONFIG_INFO;
    }
    
}
