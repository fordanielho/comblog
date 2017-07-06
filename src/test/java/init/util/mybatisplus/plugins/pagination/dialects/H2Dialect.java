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
package init.util.mybatisplus.plugins.pagination.dialects;

import init.util.mybatisplus.plugins.pagination.IDialect;

/**
 * <p>
 * H2 数据库分页方言
 * </p>
 * 
 * @author hubin
 * @Date 2016-11-10
 */
public class H2Dialect implements IDialect {

	public String buildPaginationSql(String originalSql, int offset, int limit) {
		StringBuffer sql = new StringBuffer(originalSql);
		sql.append(" limit ").append(limit);
		if (offset > 0) {
			sql.append(" offset ").append(offset);
		}
		return sql.toString();
	}

}