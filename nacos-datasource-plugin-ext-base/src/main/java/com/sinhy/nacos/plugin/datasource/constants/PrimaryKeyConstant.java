/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.constants;

/**
 * 主键常量
 * @author sinhy
 * @since 2025-04-28 13:11
 * @version V1.0
 */
public class PrimaryKeyConstant
{
    
    /**
     * replace lower Statement.RETURN_GENERATED_KEYS into id key.
     */
    public static final String[] LOWER_RETURN_PRIMARY_KEYS = new String[] {"id"};
    
    /**
     * upper replace Statement.RETURN_GENERATED_KEYS into id key.
     * using dameng database.
     */
    public static final String[] UPPER_RETURN_PRIMARY_KEYS = new String[] {"ID"};
    
}
