package com.fs.app.recsys.arithmetic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fs.app.recsys.arithmetic.recommend.BaseItemRecommend;
import com.fs.commons.app.entity.RenderData;

@Controller
@RequestMapping("/recom")
public class ArithmeticController extends BaseHandlers{

	@ResponseBody
	@RequestMapping(value = "/baseitem", method = RequestMethod.GET)
	public void baseitem(HttpServletRequest request,HttpServletResponse response) {
		System.setProperty("hadoop.home.dir", "E:\\hadoop-2.6.0");
		BaseItemRecommend recommend=new BaseItemRecommend();
		try {
			recommend.Recommend();
		} catch (Exception e) {
			System.out.println("==推荐数据出错:"+e.toString());
		}
		RenderData result=new RenderData("","猜大盘数据");
		WriteJson(response,result);
	}
}
