/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
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
package init.util.mybatisplus.toolkit;


import org.apache.ibatis.annotations.Select;
import init.util.mybatisplus.entity.CountOptimize;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * JsqlParserUtils工具类
 * </p>
 *
 * @author Caratacus
 * @Date 2016-11-30
 */
@SuppressWarnings("unchecked")
public class JsqlParserUtils {
	//private static List<SelectItem> countSelectItem = null;

	/**
	 * jsqlparser方式获取select的count语句
	 *
	 * @param originalSql
	 *            selectSQL
	 * @return
	 */
	public static CountOptimize jsqlparserCount(CountOptimize countOptimize, String originalSql) {
		String sqlCount;
		try {
//			Select selectStatement = (Select) CCJSqlParserUtil.parse(originalSql);
//			PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
//			Distinct distinct = plainSelect.getDistinct();
//			List<Expression> groupBy = plainSelect.getGroupByColumnReferences();
//			// 优化Order by
//			List<OrderByElement> orderBy = plainSelect.getOrderByElements();
//			// 添加包含groupby 不去除orderby
//			if (CollectionUtils.isEmpty(groupBy) && CollectionUtils.isNotEmpty(orderBy)) {
//				plainSelect.setOrderByElements(null);
//				countOptimize.setOrderBy(false);
//			}
//			// 包含 distinct、groupBy不优化
//			if (distinct != null || CollectionUtils.isNotEmpty(groupBy)) {
//				sqlCount = String.format(SqlUtils.SQL_BASE_COUNT, selectStatement.toString());
//				countOptimize.setCountSQL(sqlCount);
//				return countOptimize;
//			}
//			List<SelectItem> selectCount = countSelectItem();
//			plainSelect.setSelectItems(selectCount);
//			sqlCount = selectStatement.toString();
		} catch (Exception e) {
			sqlCount = String.format(SqlUtils.SQL_BASE_COUNT, originalSql);
		}
		//countOptimize.setCountSQL(sqlCount);
		return countOptimize;
	}

	/**
	 * 获取jsqlparser中count的SelectItem
	 *
	 * @return
	 */
	private static List<String> countSelectItem() {
//		if (CollectionUtils.isNotEmpty(countSelectItem)) {
//			return countSelectItem;
//		}
//		Function function = new Function();
//		function.setName("COUNT");
//		List<Expression> expressions = new ArrayList<Expression>();
//		LongValue longValue = new LongValue(1);
//		ExpressionList expressionList = new ExpressionList();
//		expressions.add(longValue);
//		expressionList.setExpressions(expressions);
//		function.setParameters(expressionList);
//		countSelectItem = new ArrayList<SelectItem>();
//		SelectExpressionItem selectExpressionItem = new SelectExpressionItem(function);
//		countSelectItem.add(selectExpressionItem);
//		return countSelectItem;
		return null;
	}
}
