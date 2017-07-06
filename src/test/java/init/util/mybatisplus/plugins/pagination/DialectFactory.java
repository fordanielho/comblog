/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package init.util.mybatisplus.plugins.pagination;

import init.util.mybatisplus.enums.DBType;
import init.util.mybatisplus.exceptions.MybatisPlusException;
import init.util.mybatisplus.plugins.pagination.dialects.*;

/**
 * <p>
 * 分页方言工厂类
 * </p>
 * 
 * @author hubin
 * @Date 2016-01-23
 */
public class DialectFactory {

	/**
	 * <p>
	 * 根据数据库类型选择不同分页方言
	 * </p>
	 * 
	 * @param dbtype
	 *            数据库类型
	 * @return
	 * @throws Exception
	 */
	public static IDialect getDialectByDbtype(String dbtype) throws Exception {
		if (DBType.MYSQL.getDb().equalsIgnoreCase(dbtype)) {
			return new MySqlDialect();
		} else if (DBType.ORACLE.getDb().equalsIgnoreCase(dbtype)) {
			return new OracleDialect();
		} else if (DBType.DB2.getDb().equalsIgnoreCase(dbtype)) {
			return new DB2Dialect();
		} else if (DBType.H2.getDb().equalsIgnoreCase(dbtype)) {
			return new H2Dialect();
		} else if (DBType.SQLSERVER.getDb().equalsIgnoreCase(dbtype)) {
			return new SQLServerDialect();
		} else if (DBType.SQLSERVER2005.getDb().equalsIgnoreCase(dbtype)) {
			return new SQLServer2005Dialect();
		} else if (DBType.POSTGRE.getDb().equalsIgnoreCase(dbtype)) {
			return new PostgreDialect();
		} else if (DBType.HSQL.getDb().equalsIgnoreCase(dbtype)) {
			return new HSQLDialect();
		} else if (DBType.SQLITE.getDb().equalsIgnoreCase(dbtype)) {
			return new SQLiteDialect();
		} else {
			throw new MybatisPlusException("The database is not supported！dbtype:" + dbtype);
		}
	}

}
