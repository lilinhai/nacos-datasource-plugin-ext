/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.enumer;

import java.util.HashMap;
import java.util.Map;

/**
 * 去掉NOW(3)，因为该函数存在兼容性问题
 * @author lilinhai
 */
public enum TrustedSqlFunctionEnum
{
    
    /**
     * NOW().
     */
    NOW("NOW()", "NOW()");
    
    private static final Map<String, TrustedSqlFunctionEnum> LOOKUP_MAP = new HashMap<>();
    
    static
    {
        for (TrustedSqlFunctionEnum entry : TrustedSqlFunctionEnum.values())
        {
            LOOKUP_MAP.put(entry.functionName, entry);
        }
    }
    
    private final String functionName;
    
    private final String function;
    
    TrustedSqlFunctionEnum(String functionName, String function)
    {
        this.functionName = functionName;
        this.function = function;
    }
    
    /**
     * Get the function name.
     *
     * @param functionName function name
     * @return function
     */
    public static String getFunctionByName(String functionName)
    {
        TrustedSqlFunctionEnum entry = LOOKUP_MAP.get(functionName);
        if (entry != null)
        {
            return entry.function;
        }
        throw new IllegalArgumentException(String.format("Invalid function name: %s", functionName));
    }
}
