package com.blog.common.base;
  
public class OracleDialect extends Dialect{  
  
    /* (non-Javadoc) 
     * @see org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String, int, int) 
     */  
    @Override  
    public String getLimitString(String sql, int offset, int limit) {  
  
        sql = sql.trim();  
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);  
          
        pagingSelect.append("select * from ( select t.*, rownum rownnum_ from ( ");  
          
        pagingSelect.append(sql);  
          
        pagingSelect.append(" )t where rownum <= ").append(offset+limit).append(" ) where rownnum_ > ").append(offset);  
          
        return pagingSelect.toString();  
    }
}