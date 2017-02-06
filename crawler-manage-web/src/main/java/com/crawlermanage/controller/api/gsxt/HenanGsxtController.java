/**
 * 
 */
package com.crawlermanage.controller.api.gsxt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crawler.domain.json.Result;
import com.crawler.gsxt.domain.json.AicFeedJson;
import com.crawlermanage.service.gsxt.GsxtService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author kingly
 * @date 2016年3月3日
 * 
 */
@Controller
@RequestMapping("/api/gsxt/henan")
public class HenanGsxtController {
	
	@Autowired
	private GsxtService gsxtService;
	private static final Logger LOGGER = LoggerFactory.getLogger(HenanGsxtController.class);
	
	
	@RequestMapping("/getSearchPage")
	@ResponseBody
	public String getSearchPage(boolean isDebug, String logback) {
		LOGGER.info("==============HenanGsxtController.getSearchPage start!=================");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		Result<Map<String, String>> result = gsxtService.searchPageHandleOfHenan(isDebug, logback);
		
		return gson.toJson(result);
	}
	
	@RequestMapping("/getData")
	@ResponseBody
	public String getData(@RequestParam("serializedFileName") String serializedFileName, 
			@RequestParam("verifycode") String verifycode, 
			@RequestParam("keyword") String keyword, boolean isDebug, String logback) {
		LOGGER.info("==============HenanGsxtController.getData start!=================");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		Result<AicFeedJson> result = gsxtService.getDataOfHenan(serializedFileName, verifycode, keyword, isDebug, logback);
		
		return gson.toJson(result);
	}
	
	@RequestMapping("/getDataOnce")
	@ResponseBody
	public String getDataOnce(@RequestParam("keyword") String keyword,
			boolean isDebug, String logback) {

		LOGGER.info("==============HenanGsxtController.getDataOnce start!=================");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Result<AicFeedJson> result = gsxtService.getDataOfHenanOnce(keyword,
				isDebug, logback);
		return gson.toJson(result);

	}
	
}


