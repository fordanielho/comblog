package com.blog.common.base;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class MyPaginationInterceptor implements Interceptor {
    //日志对象
    protected static Logger log = LoggerFactory.getLogger(MyPaginationInterceptor.class);


    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        BoundSql boundSql = statementHandler.getBoundSql();

        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");

        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        }
        //如果是自定义PageBound，则在分页前计算数据总行数
        if (rowBounds instanceof Pagination) {
            Pagination page = (Pagination) rowBounds;
            //是否需要计算总行数
            if (page.isSearchCount()) {
                Connection connection = (Connection) invocation.getArgs()[0];
                MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
                this.setTotalCount(boundSql, connection, mappedStatement, page);
            }
        }

        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");

        Dialect.Type databaseType = null;
        try {
            databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
        } catch (Exception e) {
            //ignore
        }
        if (databaseType == null) {
            throw new RuntimeException("the value of the dialect property is not defined : " + configuration.getVariables().getProperty("dialect"));
        }
        Dialect dialect = null;
        switch (databaseType) {
            case ORACLE:
                dialect = new OracleDialect();
                break;
            case MYSQL://需要实现MySQL的分页逻辑
                break;

        }

        metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit()));
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        if (log.isDebugEnabled()) {
            log.debug("生成分页SQL : " + boundSql.getSql());
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

    /**
     * 从数据库里查询总的记录数，回写进分页参数<code>PageBound</code>,这样调用
     * 者就可用通过 分页参数<code>PageBound</code>获得相关信息。
     *
     * @param
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param
     */
    private void setTotalCount(BoundSql boundSql, Connection connection, MappedStatement mappedStatement, Pagination pageBound) {
        // 记录总记录数
        String countSql = "select count(0) from (" + boundSql.getSql() + ")";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            //分页支持foreach标签
            Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
            if (metaParamsField != null) {
                MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
                ReflectUtil.setValueByFieldName(countBS, "metaParameters", mo);
            }
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            long totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getLong(1);
            }
            pageBound.setTotal((int) totalCount);
            long pagecount = 0;
            if (pageBound.getSize() > 0) {
                pagecount = pageBound.getTotal() / pageBound.getSize() + ((pageBound.getTotal() % pageBound.getSize() == 0) ? 0 : 1);
            }
            pageBound.setTotal((int) pagecount);
        } catch (SQLException e) {
            log.error("获取总记录数异常", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                countStmt.close();
            } catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 对SQL参数(?)设值
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }
}
