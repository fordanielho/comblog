package com.blog.common.base;

import com.baomidou.mybatisplus.entity.CountOptimize;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.toolkit.IOUtils;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.baomidou.mybatisplus.toolkit.SqlUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class PaginationInterceptorExt implements Interceptor {
    private static final Log logger = LogFactory.getLog(PaginationInterceptor.class);
    private boolean overflowCurrent = false;
    private String optimizeType = "default";
    private String dialectType;
    private String dialectClazz;

    public PaginationInterceptorExt() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if(target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");
            if(rowBounds == null || rowBounds == RowBounds.DEFAULT) {
                return invocation.proceed();
            }

            BoundSql boundSql = (BoundSql)metaStatementHandler.getValue("delegate.boundSql");
            String originalSql = boundSql.getSql();
            if(rowBounds instanceof Pagination) {
                MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
                Pagination page = (Pagination)rowBounds;
                boolean orderBy = true;
                if(page.isSearchCount()) {
                    CountOptimize countOptimize = SqlUtils.getCountOptimize(originalSql, this.optimizeType, this.dialectType, page.isOptimizeCount());
                    orderBy = countOptimize.isOrderBy();
                    Connection connection = (Connection) invocation.getArgs()[0];
                    this.count(connection,countOptimize.getCountSQL(), mappedStatement, boundSql, page);
                    if(page.getTotal() <= 0) {
                        return invocation.proceed();
                    }
                }

                String buildSql = SqlUtils.concatOrderBy(originalSql, page, orderBy);
                originalSql = DialectFactory.buildPaginationSql(page, buildSql, this.dialectType, this.dialectClazz);
            } else {
                originalSql = DialectFactory.buildPaginationSql(rowBounds, originalSql, this.dialectType, this.dialectClazz);
            }

            metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
            metaStatementHandler.setValue("delegate.rowBounds.offset", Integer.valueOf(0));
            metaStatementHandler.setValue("delegate.rowBounds.limit", Integer.valueOf(2147483647));
        }

        return invocation.proceed();
    }

    protected void count(Connection connection, String sql, MappedStatement mappedStatement, BoundSql boundSql, Pagination page) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        // 记录总记录数
        String countSql = "select count(0) from (" + boundSql.getSql() + ")";
        try {
            statement = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            //分页支持foreach标签
            Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
            if(metaParamsField != null){
                MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
                ReflectUtil.setValueByFieldName(countBS, "metaParameters", mo);
            }
            setParameters(statement, mappedStatement, countBS, boundSql.getParameterObject());
            resultSet = statement.executeQuery();
            int total = 0;
            if(resultSet.next()) {
                total = resultSet.getInt(1);
            }

            page.setTotal(total);
            if(this.overflowCurrent && page.getCurrent() > page.getPages()) {
                page = new Pagination(1, page.getSize());
                page.setTotal(total);
            }
        } catch (Exception var13) {
            logger.error("Error: query page count total fail!", var13);
        } finally {
            IOUtils.closeQuietly(statement);
            IOUtils.closeQuietly(resultSet);
        }

    }

    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this):target;
    }

    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if(StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }

        if(StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }

    }

    public void setDialectType(String dialectType) {
        this.dialectType = dialectType;
    }

    public void setDialectClazz(String dialectClazz) {
        this.dialectClazz = dialectClazz;
    }

    public void setOverflowCurrent(boolean overflowCurrent) {
        this.overflowCurrent = overflowCurrent;
    }

    public void setOptimizeType(String optimizeType) {
        this.optimizeType = optimizeType;
    }

    /**
     * 对SQL参数(?)设值
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException{
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }
}
