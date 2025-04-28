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

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.GroupCapacityMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.dialect.OracleDatabaseDialect;
import com.sinhy.nacos.plugin.datasource.impl.base.BaseGroupCapacityMapper;

/***
 * @author lilinhai
 */
public class GroupCapacityMapperByOracle extends BaseGroupCapacityMapper implements GroupCapacityMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 11:33
     * @param databaseDialect
     */
    public GroupCapacityMapperByOracle()
    {
        super(new OracleDatabaseDialect());
    }
    
    @Override
    public MapperResult selectGroupInfoBySize(MapperContext context)
    {
        String sql = databaseDialect.getLimitTopSqlWithMark("SELECT id, group_id FROM group_capacity WHERE id > ?");
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.ID), context.getPageSize()));
    }
    
    @Override
    public String getDataSource()
    {
        return DatabaseTypeConstant.ORACLE;
    }
}
