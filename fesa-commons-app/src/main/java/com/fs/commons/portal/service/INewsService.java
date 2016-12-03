package com.fs.commons.portal.service;

import com.fs.commons.app.entity.RenderData;

public interface INewsService {
	/*
	 * 收藏&点赞新闻:1:收藏。2:赞
	 */
	public RenderData updateNewsRead(Integer userid,Integer newsid,Integer type);
	
	/*
	 * 获取新闻的评论或赞
	 */
	public RenderData getReadByNewId(Integer newsid,Integer type);
	/*
	 * 增加新闻评论
	 */
	public RenderData updateNewComment(Integer userid,Integer newsid,String commentInfo);
	
	/*
	 * 获取新闻的评论信息
	 */
	public RenderData getCommentByNewId(Integer newsid);
}
