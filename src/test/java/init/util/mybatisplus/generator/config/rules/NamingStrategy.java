/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package init.util.mybatisplus.generator.config.rules;

import init.util.mybatisplus.generator.config.ConstVal;
import init.util.mybatisplus.toolkit.StringUtils;

/**
 * 从数据库表到文件的命名策略
 *
 * @author YangHu, tangguo
 * @since 2016/8/30
 */
public enum NamingStrategy {
	/**
	 * 不做任何改变，原样输出
	 */
	nochange,
	/**
	 * 下划线转驼峰命名
	 */
	underline_to_camel,
	/**
	 * 仅去掉前缀
	 */
	remove_prefix,
	/**
	 * 去掉前缀并且转驼峰
	 */
	remove_prefix_and_camel;

	public static String underlineToCamel(String name) {
	// 快速检查
		if (StringUtils.isEmpty(name)) {
			// 没必要转换
			return "";
		}
		String[] tmp = name.split("_");
		String result = null;
		for(int n = 0; n < tmp.length ;n++){
			if(n == 0){
				result = tmp[n].toLowerCase();
			}else{
				result += tmp[n].substring(0, 1) + tmp[n].substring(1).toLowerCase();
			}
		}
		return result;
	}

	/**
	 * 去掉下划线前缀
	 *
	 * @param name
	 * @return
	 */
	public static String removePrefix(String name) {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		int idx = name.indexOf(ConstVal.UNDERLINE);
		if (idx == -1) {
			return name;
		}
		return name.substring(idx + 1);
	}

	/**
	 * 去掉指定的前缀
	 * 
	 * @param name
	 * @param prefix
	 * @return
	 */
	public static String removePrefix(String name, String[] prefix) {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		if (null != prefix) {
			for (String pf : prefix) {
				if (name.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
					// 判断是否有匹配的前缀，然后截取前缀
					// 删除前缀
					return name.substring(pf.length());
				}
			}
		}
		return name;
	}

	/**
	 * 去掉下划线前缀且将后半部分转成驼峰格式
	 *
	 * @param name
	 * @param tablePrefix
	 * @return
	 */
	public static String removePrefixAndCamel(String name, String[] tablePrefix) {
		return underlineToCamel(removePrefix(name, tablePrefix));
	}

	/**
	 * 实体首字母大写
	 *
	 * @param name
	 *            待转换的字符串
	 * @return 转换后的字符串
	 */
	public static String capitalFirst(String name) {
		if (StringUtils.isNotEmpty(name)) {
			return name.substring(0, 1).toUpperCase() + name.substring(1);
			/*char[] array = name.toCharArray();
			array[0] -= 32;
			return String.valueOf(array);*/
		}
		return "";
	}

}
