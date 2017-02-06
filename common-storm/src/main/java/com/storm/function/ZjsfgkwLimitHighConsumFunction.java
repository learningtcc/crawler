package com.storm.function;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crawler.domain.json.StatusCodeDef;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.module.htmlunit.WebCrawler;
import com.module.log.redis.ChannelLogger;
import com.module.log.redis.ChannelLoggerFactory;
import com.storm.domian.WebParam;

public class ZjsfgkwLimitHighConsumFunction {
	
	public String search(WebParam webParam) throws Exception {
		ChannelLogger LOGGER = ChannelLoggerFactory.getLogger(ZjsfgkwLimitHighConsumFunction.class, webParam.getLogback());
		Map<String, String> resultMap = new LinkedHashMap<String, String>();
		Gson gson = new GsonBuilder().create();
		LOGGER.info("==================ZjsfgkwLimitHighConsumFunction.search start!======================");
		WebClient webClient = WebCrawler.getInstance().getWebClient();

		HtmlPage searchPage = webClient.getPage(webParam.getUrl());
		
		System.out.println("ZjsfgkwLimitHighConsumFunction页面数据searchPage.asXml()="+searchPage.asXml());
		
		if(searchPage.getElementsByIdAndOrName("txtReallyName").size()==0){
			LOGGER.returnRedisResource();
			resultMap.put("statusCodeDef", StatusCodeDef.FREQUENCY_LIMITED);
			resultMap.put("searchPageHtml", searchPage.asXml());
			return gson.toJson(resultMap);
		}
		
		//请求参数
		Map<String, String> params=uRLRequest(webParam.getUrl());
		HtmlTextInput txtReallyName = (HtmlTextInput)(searchPage.getElementsByIdAndOrName("txtReallyName").get(0)); 
		txtReallyName.setText(params.get("txtreallyname"));
		HtmlTextInput txtCredentialsNumber = (HtmlTextInput)(searchPage.getElementsByIdAndOrName("txtCredentialsNumber").get(0)); 
		txtCredentialsNumber.setText(params.get("txtcredentialsnumber"));
		HtmlTextInput txtAH = (HtmlTextInput)(searchPage.getElementsByIdAndOrName("txtAH").get(0)); 
		txtAH.setText(params.get("txtah"));
		HtmlSelect drpZXFY = (HtmlSelect)(searchPage.getElementsByIdAndOrName("drpZXFY").get(0)); 
		if((params.get("drpzxfy")).equals("")){
			drpZXFY.getOptionByText("全部").setSelected(true);
		}else{
			drpZXFY.getOptionByText(params.get("drpzxfy")).setSelected(true);
		}
		HtmlTextInput txtStartLARQ = (HtmlTextInput)(searchPage.getElementsByIdAndOrName("txtStartLARQ").get(0)); 
		txtStartLARQ.setText(params.get("txtstartlarq"));
		HtmlTextInput txtEndLARQ = (HtmlTextInput)(searchPage.getElementsByIdAndOrName("txtEndLARQ").get(0)); 
		txtEndLARQ.setText(params.get("txtendlarq"));

	
		//点击提交请求
		HtmlElement personalButton = (HtmlElement)(searchPage.getElementsByIdAndOrName("btnSearch").get(0)); 
		HtmlPage HtmPage = personalButton.click();
//		System.out.println(loggedPage.asXml());
		
		LOGGER.returnRedisResource();
		
		resultMap.put("statusCodeDef", StatusCodeDef.SCCCESS);
		resultMap.put("searchPageHtml", HtmPage.asXml());
		return gson.toJson(resultMap);
	}
	
/*		public static void main(String[] args) throws Exception {
			ZjsfgkwCreditPersonalFunction zjsfgkwCreditPersonalFunction=new ZjsfgkwCreditPersonalFunction();
	String url =  "http://www.zjsfgkw.cn/Execute/CreditPersonal?txtReallyName="+
			  "&txtCredentialsNumber=&txtAH=&drpZXFY=&txtStartLARQ=&txtEndLARQ="; 
	WebParam webParam = new WebParam();
	webParam.setUrl(url);
	webParam.setUnit(UtilDefinition.JSOUP);
	String asXml=zjsfgkwCreditPersonalFunction.search(webParam);
	System.out.println(asXml);
        }*/
	
	/**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public  Map<String, String> uRLRequest(String URL)
    {
    Map<String, String> mapRequest = new HashMap<String, String>();
   
      String[] arrSplit=null;
     
    String strUrlParam=truncateUrlPage(URL);
    if(strUrlParam==null)
    {
        return mapRequest;
    }
      //每个键值为一组 www.2cto.com
    arrSplit=strUrlParam.split("[&]");
    for(String strSplit:arrSplit)
    {
          String[] arrSplitEqual=null;         
          arrSplitEqual= strSplit.split("[=]");
         
          //解析出键值
          if(arrSplitEqual.length>1)
          {
              //正确解析
              mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
             
          }
          else
          {
              if(arrSplitEqual[0]!="")
              {
              //只有参数没有值，不加入
              mapRequest.put(arrSplitEqual[0], "");       
              }
          }
    }   
    return mapRequest;   
    }
    
    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private  String truncateUrlPage(String strURL)
    {
    String strAllParam=null;
      String[] arrSplit=null;
     
      strURL=strURL.trim().toLowerCase();
     
      arrSplit=strURL.split("[?]");
      if(strURL.length()>1)
      {
          if(arrSplit.length>1)
          {
                  if(arrSplit[1]!=null)
                  {
                  strAllParam=arrSplit[1];
                  }
          }
      }
     
    return strAllParam;   
    }
	
}
