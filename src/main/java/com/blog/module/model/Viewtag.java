package com.blog.module.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.blog.common.base.BaseModel;


/**
 * <p>
 * 博客id和标签名
 * </p>
 *
 * @author hejj
 * @since 2017-07-06
 */
@TableName("view_tag")
public class Viewtag extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名
     */
	private String name;
    /**
     * 博客id
     */
	private Integer vid;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

}
