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

import java.util.Collections;

import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.dialect.SqlServerDatabaseDialect;
import com.sinhy.nacos.plugin.datasource.impl.base.BaseConfigInfoTagMapper;

/**
 * The SQL Server implementation of ConfigInfoTagMapper.
 *
 * @author lilinhai
 **/

public class ConfigInfoTagMapperBySqlServer extends BaseConfigInfoTagMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 11:49
     */
    public ConfigInfoTagMapperBySqlServer()
    {
        super(new SqlServerDatabaseDialect());
    }
    
    @Override
    public MapperResult findAllConfigInfoTagForDumpAllFetchRows(MapperContext context)
    {
        String sql = " SELECT t.id,data_id,group_id,tenant_id,tag_id,app_name,content,md5,gmt_modified " + " FROM ( SELECT id FROM config_info_tag  ORDER BY id OFFSET " + context.getStartRow()
                + " ROWS FETCH NEXT " + context.getPageSize() + " ROWS ONLY ) " + "g, config_info_tag t  WHERE g.id = t.id  ";
        return new MapperResult(sql, Collections.emptyList());
    }
    
    @Override
    public String getDataSource()
    {
        return DatabaseTypeConstant.SQLSERVER;
    }
}
