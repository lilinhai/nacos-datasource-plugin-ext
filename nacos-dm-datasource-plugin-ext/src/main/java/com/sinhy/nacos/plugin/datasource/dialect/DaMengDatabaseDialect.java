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

package com.sinhy.nacos.plugin.datasource.dialect;

import com.sinhy.nacos.plugin.datasource.constants.DatabaseTypeConstant;
import com.sinhy.nacos.plugin.datasource.constants.PrimaryKeyConstant;

/**
 * dameng database dialect.
 *
 * @author lilinhai
 */
public class DaMengDatabaseDialect extends AbstractDatabaseDialect
{
    
    @Override
    public String[] getReturnPrimaryKeys()
    {
        return PrimaryKeyConstant.UPPER_RETURN_PRIMARY_KEYS;
    }
    
    @Override
    public String getType()
    {
        return DatabaseTypeConstant.DM;
    }
    
}
