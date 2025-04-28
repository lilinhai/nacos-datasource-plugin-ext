/*
 * 
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

import com.alibaba.nacos.plugin.datasource.mapper.TenantInfoMapper;
import com.sinhy.nacos.plugin.datasource.dialect.OracleDatabaseDialect;

/***
 * @author lilinhai
 */
public class TenantInfoMapperByOracle extends AbstractOracleMapper implements TenantInfoMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author sinhy
     * @since 2025-04-27 11:44
     * @param databaseDialect
     */
    public TenantInfoMapperByOracle()
    {
        super(new OracleDatabaseDialect());
    }
    
}
