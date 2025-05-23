/*
 * Copyright 1999-2022 Alibaba Group Holding Ltd.
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

package com.sinhy.nacos.plugin.datasource.impl.mssql;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.dialect.SqlServerDatabaseDialect;
import com.sinhy.nacos.plugin.datasource.impl.base.BaseTenantCapacityMapper;

/**
 * The SQL Server implementation of TenantCapacityMapper.
 *
 * @author lilinhai
 **/
public class TenantCapacityMapperBySqlServer extends BaseTenantCapacityMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 11:49
     */
    public TenantCapacityMapperBySqlServer()
    {
        super(new SqlServerDatabaseDialect());
    }
    
    @Override
    public MapperResult getCapacityList4CorrectUsage(MapperContext context)
    {
        String sql = "SELECT id, tenant_id FROM tenant_capacity WHERE id > ? ORDER BY id " + " OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY ";
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.ID), context.getWhereParameter(FieldConstant.LIMIT_SIZE)));
    }
    
    @Override
    public String getDataSource()
    {
        return DatabaseTypeConstant.SQLSERVER;
    }
    
}
