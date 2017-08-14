package com.blog.module.model;

import com.blog.common.base.BaseModel;

import java.util.Date;


/**
 * <p>
 * 自己开发的项目
 * </p>
 *
 * @author hejj
 * @since 2017-07-06
 */
public class Project extends BaseModel {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
     * 项目名称
     */
	private String name;
    /**
     * 项目url地址，例如https://github.com/jcalaz/jcalaBlog
     */
	private String url;
    /**
     * 项目所用技术
     */
	private String tech;
    /**
     * 项目描述
     */
	private String desp;
    /**
     * 项目创建时间
     */
	private Date date;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
