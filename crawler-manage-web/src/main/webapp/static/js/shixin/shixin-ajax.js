//从接口处获取详情
function shixinAjax_getDetailFromInterface(ctx,async,data,callFunName){
	    
	     var getDetailFromInterfaceResult='';
	     $.ajax({
	    	      url:ctx+"/api/shixin/getShixinDataOnce",
	    	      type:"post",
	    	      async:async,
	    	      data:data,
	    	      success:function(data){
	    	    	     if(!async){
	    	    	    	 getDetailFromInterfaceResult=data;
	    	    	     }   
	    	      },
	    	      error:function(){
	    	    	  if(!async){
	    	    	    	 getDetailFromInterfaceResult="isError";
	    	    	     } 
	    	      }
	     })
	     
	     if(!async){
	    	   return getDetailFromInterfaceResult;
	     }
}
//以post方式向详情页传递参数
function shixinAjax_windowOpenPostDetail(url,name,content){
	 
	     var form=$("<form>");
	     form.attr("style","display:none");
	     form.attr("method","post");
	     form.attr("target","_blank");
	     form.attr("action",url);
	     
	     var input=$("<input>");
	     input.attr("style","display:none");
	     input.attr("name",name);
	     input.attr("value",content);
	     
	     $("body").append(form);
	     form.append(input);
	     
	     form.submit();
	     form.remove();
}
//以post方式传递调试内容
function shixinAjax_windowOpenPostInterface(url,data,dataContent,error,errorContent){
	 
	     var form=$("<form>");
	     form.attr("style","display:none");
	     form.attr("method","post");
	     form.attr("target","_blank");
	     form.attr("action",url);
	     
	     var input1=$("<input>");
	     input1.attr("style","display:none");
	     input1.attr("name",data);
	     input1.attr("value",dataContent);
	     
	     
	     var input2=$("<input>");
	     input2.attr("style","display:none");
	     input2.attr("name",error);
	     input2.attr("value",errorContent);
	     
	     $("body").append(form);
	     form.append(input1);
	     form.append(input2);
	     
	     form.submit();
	     form.remove();
}
//检查企业是否存，若存在，从数据库获取企业详情;不存在，直接返回
function shixinAjax_checkIsExist(ctx,async,type,keyword,cardNum,callFunName){
	
	     var getDetailFromDBResult='';
	     
	     $.ajax({
	    	 
	    	     url:ctx+"/modules/shixin/checkIsExist",
	    	     type:"post",
	    	     async:async,
	    	     data:{
	    	    	 type:type,
	    	    	 keyword:keyword,
	    	    	 cardNum:cardNum,
	    	    	 time:new Date()
	    	     },
	    	     success:function(data){
	    	    	 
	    	    	   if(!async){
	    	    		   getDetailFromDBResult=data; 
	    	    	   }
	    	     },
	    	     error:function(){
	    	    	   if(!async){
	    	    		   getDetailFromDBResult="isError";
	    	    	   }
	    	     }
	    	 
	     })
	     
	     if(!async){
	    	  return getDetailFromDBResult;
	     }
	
}