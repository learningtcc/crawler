package com.module.ocr.service;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.module.ocr.utils.AbstractChaoJiYingHandler;
import com.module.ocr.utils.ChaoJiYingUtils;
import com.module.ocr.utils.IVerifycodeHandler;


@Component
public class RenfawangOCRService extends AbstractChaoJiYingHandler {
	private static final String CODETYPE = "1104" ;// "6003";//"1104"; //验证码类型 参照 http://www.chaojiying.com/price.html  似乎越贵越通用啊
	private static final String LEN_MIN = "0";
	private static final String TIME_ADD = "0";
	private static final String STR_DEBUG = "a";
	
	@Override
	public String getVerifycode(File file) throws Exception {
		Gson gson = new GsonBuilder().create();
		String chaoJiYingResult = super.getVerifycodeByChaoJiYing(CODETYPE, LEN_MIN, TIME_ADD, STR_DEBUG, file.getAbsolutePath()); //超级鹰返回结果 参照 http://www.chaojiying.com/api-10.html
		
		String errNo = ChaoJiYingUtils.getErrNo(chaoJiYingResult);
		if (!ChaoJiYingUtils.RESULT_SUCCESS.equals(errNo)) {
			return ChaoJiYingUtils.getErrMsg(errNo);
		}
		
		return (String) gson.fromJson(chaoJiYingResult, Map.class).get("pic_str");
	}
	
	
	public static void main(String[] args) throws Exception {
		IVerifycodeHandler verifycodeHandler = new RenfawangOCRService();
		String verifycode = verifycodeHandler.getVerifycode(new File("D:\\eclipse\\d3996ea8-95d0-4951-b73a-4b7212a4d770.jpg"));
		System.out.println(verifycode);
	} 
	
}
