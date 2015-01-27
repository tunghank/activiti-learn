package com.cista.report.action;


import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.struts2.ServletActionContext;
//Dao
import com.cista.report.dao.StandardCostDao;
import com.cista.report.dao.AssemblyYieldDao;
//To
import com.cista.system.to.ExtJSGridTo;
import com.cista.report.to.StandardCostTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldIssueTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldQueryTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldReceiveTo;
import com.cista.report.to.AssemblyYield.AssemblyYieldTo;
//Base
import com.cista.system.util.BaseAction;
import com.cista.system.util.CistaUtil;
//Json
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sun.java_cup.internal.internal_error;


public class AssemblyYield extends BaseAction{
	

	private static final long serialVersionUID = 1L;
	
	public String AssemblyYieldPre() throws Exception{
			
		request= ServletActionContext.getRequest();
		
		return SUCCESS;
	}

	public String AssemblyYieldReport() throws Exception{
		
		try {

			//for paging        
	        int total;//分頁。。。數據總數       
	        int iStart=0;//分頁。。。每頁開始數據
	        int iLimit=10;//分頁。。。每一頁數據
	        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
	        
			String query = request.getParameter("query");
			query = null != query ? query : "";
			logger.debug("query " + query);
			
			Gson gson = new Gson();		

	        // 創建一個JsonParser  
	        JsonParser parser = new JsonParser();  
	        JsonElement jsonEl = parser.parse(query);            
	        JsonObject jsonObj = null;  
	        jsonObj = jsonEl.getAsJsonObject();//轉換成Json對象
	        
	        JsonElement queryEl =jsonObj.get("query");//query 節點
			AssemblyYieldQueryTo queryTo = gson.fromJson(queryEl.toString(), AssemblyYieldQueryTo.class);
	
			
	        String project = queryTo.getProject();
	        project = null != project ? project : "";
	        
            String edate = queryTo.getEdate();
            edate = null != edate ? edate : "";
            if(!edate.equals("")){
            	Date dEdate = df1.parse(edate);
            	edate = df2.format(dEdate);
            }

			
			
			AssemblyYieldDao assemblyYieldDao = new AssemblyYieldDao();
			StandardCostDao standardCostDao = new StandardCostDao();
			List<StandardCostTo> standardCostList = standardCostDao.getStandardCostByProject(project);
			List<AssemblyYieldTo> assemblyYieldList = new ArrayList<AssemblyYieldTo>();
			//分頁
			String product;
			
			if( standardCostList!= null){
				
				for(int i=0;i < standardCostList.size(); i ++){
					StandardCostTo standardCostTo = standardCostList.get(i);
					AssemblyYieldTo assemblyYieldTo = new AssemblyYieldTo();
					product = standardCostTo.getProduct();
					assemblyYieldTo.setProduct(product);
					
					
					List<AssemblyYieldIssueTo> issueList = assemblyYieldDao.getAssemblyPOIssuedQty(edate, product);
					long issueQty =0, grossDie=0, issueDieQty=0;
					String unit="";
					if( issueList != null){
						for(int j=0; j< issueList.size(); j++){
							AssemblyYieldIssueTo issueTo = issueList.get(j);
							grossDie = issueTo.getGrossDie();
							issueQty = issueQty + issueTo.getIssueQty();
							issueDieQty = issueDieQty + issueTo.getIssueDieQty();
							unit = issueTo.getUnit();
						}
						logger.debug(product + " issueList size " + issueList.size() );
					}
					
					List<AssemblyYieldReceiveTo> receiveList =  assemblyYieldDao.getAssemblyReceiveQty(edate, product);
					long receiveDieQty=0;
					if( receiveList != null){
						for(int k=0; k< receiveList.size(); k++){
							AssemblyYieldReceiveTo receiveTo = receiveList.get(k);
							receiveDieQty = receiveDieQty + receiveTo.getReceiveQty();
						}
						logger.debug(product + " receiveList size " + receiveList.size() );
					}
					double assemblyYield=0.0;
					String strAssemblyYield="";
					if( receiveList != null & issueList != null){
						 /**
						  * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指
						  * 定精度，以後的數字四捨五入。
						  * @param v1 被除數
						  * @param v2 除數
						  * @param scale 表示表示需要精確到小數點以後幾位。
						  * @return 兩個參數的商
						 */
						BigDecimal b1 = new BigDecimal(Double.toString(receiveDieQty));
						BigDecimal b2 = new BigDecimal(Double.toString(issueDieQty));
						assemblyYield = b1.divide(b2,4,BigDecimal.ROUND_HALF_UP).doubleValue();
						
						strAssemblyYield = String.valueOf(assemblyYield * 100) + "%";
					}
					
					assemblyYieldTo.setAssemblyYield(assemblyYield);
					assemblyYieldTo.setGrossDie(grossDie);
					assemblyYieldTo.setIssueDieQty(issueDieQty);
					assemblyYieldTo.setIssueQty(issueQty);
					assemblyYieldTo.setUnit(unit);
					assemblyYieldTo.setReceiveDieQty(receiveDieQty);
					assemblyYieldTo.setStrAssemblyYield(strAssemblyYield);
					assemblyYieldList.add(assemblyYieldTo);
				}

				total=assemblyYieldList.size();
				logger.debug("total " + total);
				
				int end=iStart+iLimit;
				if(end > total){//不能總數
					end = total;
				}		
				
				List<AssemblyYieldTo> resultList = new ArrayList<AssemblyYieldTo>();
				for(int i=iStart;i<end;i++){//只加載當前頁面數據
					//logger.debug(userList.get(i).toString());
					resultList.add(assemblyYieldList.get(i));
				}
				
				ExtJSGridTo extJSGridTo = new ExtJSGridTo();
				extJSGridTo.setTotal(total);
				extJSGridTo.setRoot(resultList);
				
				String jsonData = gson.toJson(extJSGridTo);
				//logger.debug(jsonData);
				
				// 1.5 Set AJAX response
				CistaUtil.ajaxResponseData(response, jsonData);
			}else{
				CistaUtil.ajaxResponseData(response, "");
			}

		} catch (Exception e) {
			this.addActionMessage("ERROR");
			e.printStackTrace();
			logger.error(e.toString());
			addActionMessage(e.toString());
          	//AJAX
          	try{
  		    	response.setContentType("text/html; charset=UTF-8");
  				PrintWriter out = response.getWriter();
  				String returnResult = "ERROR" ;

  				logger.debug(returnResult);
  				logger.debug("Error");
  				out.print(returnResult);
  				out.close();
          	}catch(Exception ex){
          		ex.printStackTrace();
                logger.error(ex.toString());
                return NONE;
          	}

		}
		
   		return NONE;
	}
	
	
   
}
