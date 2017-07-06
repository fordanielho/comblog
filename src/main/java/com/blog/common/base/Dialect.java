package com.blog.common.base;
  
public abstract class Dialect {  
  
    public static enum Type{  
        MYSQL,  
        ORACLE  
    }  
      
    public abstract String getLimitString(String sql, int skipResults, int maxResults);  
      
}  