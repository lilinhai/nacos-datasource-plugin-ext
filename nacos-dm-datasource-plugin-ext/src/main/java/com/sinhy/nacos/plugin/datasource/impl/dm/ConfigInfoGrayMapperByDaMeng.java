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

package com.sinhy.nacos.plugin.datasource.impl.dm;

import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.constants.PrimaryKeyConstant;
import com.sinhy.nacos.plugin.datasource.dialect.DaMengDatabaseDialect;
import com.sinhy.nacos.plugin.datasource.impl.base.BaseConfigInfoGrayMapper;

/**
 * The dameng implementation of ConfigInfoAggrMapper.
 *
 * @author lilinhai
 **/

public class ConfigInfoGrayMapperByDaMeng extends BaseConfigInfoGrayMapper
{
    
    /**
     * <pre>构造方法</pre>
     * 
     * @author lilinhai
     * @since 2025-04-27 12:00
     */
    public ConfigInfoGrayMapperByDaMeng()
    {
        super(new DaMengDatabaseDialect());
    }
    
    @Override
    public String getDataSource()
    {
        return DatabaseTypeConstant.DM;
    }
    
    @Override
    public String[] getPrimaryKeyGeneratedKeys()
    {
        return PrimaryKeyConstant.UPPER_RETURN_PRIMARY_KEYS;
    }
}
