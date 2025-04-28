/* 
 * Copyright   : Sinhy Technologies Co., Ltd. Copyright 2025-2030, All right reserved.
 * @since 2025-04-28 16:05
 * @version V2.0
 */

package com.sinhy.nacos.plugin.datasource.dialect;

/**
 * 数据库方言接口定义
 */
public interface DatabaseDialect
{
    
    /**
     * get database type.
     * 
     * @return return database type name
     */
    public String getType();
    
    /**
     * get frist index page param.
     * 
     * @param page     current pageNo
     * @param pageSize current pageSize
     * @return offset val or maxRange
     */
    public int getPagePrevNum(int page, int pageSize);
    
    /**
     * get second index page param.
     * 
     * @param page     current pageNo
     * @param pageSize current pageSize
     * @return limit val or minRange
     */
    public int getPageLastNum(int page, int pageSize);
    
    /**
     * get page limit top data sql,contain placeholder.
     * 
     * @param sql orign sql
     * @return append limit sql
     */
    public String getLimitTopSqlWithMark(String sql);
    
    /**
     * get page limit page data sql,contain placeholder.
     * 
     * @param sql orign sql
     * @return append limit sql
     */
    public String getLimitPageSqlWithMark(String sql);
    
    /**
     * get page limit page data sql,using number.
     * 
     * @param sql      orign sql
     * @param pageNo   current pageNo
     * @param pageSize current pageSize
     * @return contain page number param sql
     */
    public String getLimitPageSql(String sql, int pageNo, int pageSize);
    
    /**
     * get page limit page data sql,using offset.
     * 
     * @param sql         orign sql
     * @param startOffset current offset row
     * @param pageSize    current pageSize
     * @return contain page number param sql
     */
    public String getLimitPageSqlWithOffset(String sql, int startOffset, int pageSize);
    
    /**
     * get database return primary keys.
     * 
     * @return
     */
    public String[] getReturnPrimaryKeys();
    
}
