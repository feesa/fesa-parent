package com.fs.app.portal.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fs.commons.app.pojo.RecommendDetailPojo;
import com.fs.commons.app.pojo.RecommendSpeciesPojo;
import com.fs.commons.portal.repository.IRecommendDetailRepository;
import com.fs.commons.portal.repository.IRecommendSpeciesRepository;

@Controller
@RequestMapping("/recommend")
public class RecommendHandlers extends BaseHandlers{

	@Autowired
	private IRecommendSpeciesRepository recommService;
	
	@Autowired
	private IRecommendDetailRepository detailService;
	
	@ResponseBody
	@RequestMapping(value="/getRecommendSpecies",method=RequestMethod.GET)
	public void getRecommendSpecies(HttpServletRequest request, HttpServletResponse response){
		List<RecommendSpeciesPojo> recommends=recommService.getrecommends();
		WriteJson(response,recommends);
	}
	
	@ResponseBody
	@RequestMapping(value="/getRecommendDetail",method=RequestMethod.GET)
	public void getRecommendDetail(HttpServletRequest request, HttpServletResponse response){
		int speid=Integer.valueOf(request.getParameter("speid"));
		List<RecommendDetailPojo> recommendchilds=detailService.getreDetails(speid);
		WriteJson(response,recommendchilds);
	}
}
