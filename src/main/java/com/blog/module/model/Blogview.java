package com.blog.module.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.blog.common.base.BaseModel;

import java.util.Date;


/**
 * <p>
 * 博客具体的博客
 * </p>
 *
 * @author hejj
 * @since 2017-07-06
 */
@TableName("blog_view")
public class Blogview extends BaseModel {

    private static final long serialVersionUID = 1L;

	private Integer vid;
    /**
     * 博客创建日期
     */
	private Date date;
    /**
     * 博客标题，不可为空
     */
	private String title;
    /**
     * 博客内容的html文本
     */
	private String article;
    /**
     * 标签，不同标签以,隔开
     */
	private String tags;
    /**
     * 博客内容的markdown文本
     */
	private String md;


	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getMd() {
		return md;
	}

	public void setMd(String md) {
		this.md = md;
	}

}
