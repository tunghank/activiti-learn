package com.cista.flow.test;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.util.List;

/**
 * 使用API的步驟
 * 1. 創建流程引擎ProcessEngine
 * 2. 獲取相官服務對象實例
 * 3. 使用服務對象相關方法完成流程操作
 * 
 * @author HANK
 *
 */

public class ProcessDefinitionTest {
	// 1. 創建流程引擎ProcessEngine
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	// 2. 創建倉庫服務對象
	private RepositoryService repositoryService = this.processEngine.getRepositoryService();
	
	/**
	 * A.發布規則
	 * 		成功發佈後, 會在資料庫中3張Table添加數據
	 * 		act_ge_bytearray : 添加兩條數據(規則文件和流程圖片)
	 * 		act_re_deployment : 添加一條數據(定義新流程的顯示別名和發布時間)
	 * 		act_re_procdef : 添加一條數據(流程歸則相關訊息)
	 *      	ID={key}:{version}:{流水序號}
	 * @throws Exception
	 */
	
	@Test
	public void deployProcess() throws Exception {
		
	
		//3.使用服務對象相關方法完成操作
		//3.1 創建發布對象
		DeploymentBuilder deploymentBuilder = this.repositoryService.createDeployment();
		//3.2 指定發布相關文件(流程規則文件,圖片)
		//deploymentBuilder.addClasspathResource("\\diagrams\\HelloWorld.bpmn").addClasspathResource("\\diagrams\\HelloWorld.png");
		InputStream inputStreamBpm = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("\\diagrams\\LeaveFlow.bpmn");
		InputStream inputStreamImage = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("\\diagrams\\LeaveFlow.png");
		deploymentBuilder.name("請假流程")
						.addInputStream("LeaveFlow.bpmn", inputStreamBpm)//添加Flow
						.addInputStream("LeaveFlow.png", inputStreamImage);//添加圖片
		//3.3 發布流程(使用deploy方法發布流程)
		deploymentBuilder.deploy();
	}
	
	//使用ZIP 方式發布流程
	/**
	 * this.getClass().getResourceAsStream("LeaveFlow.zip"); 在當前class所在下指定名稱文件的輸入
	 * this.getClass().getResourceAsStream("/LeaveFlow.zip"); 從classpath目錄下加載指定名稱文件的輸入法
	 * this.getClass().getContextClassLoader().getResourceAsStream("LeaveFlow.zip"); 從classpath目錄下加載指定名稱文件的輸入法
	 * Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("LeaveFlow.zip"); 從classpath目錄下加載指定名稱文件的輸入法
	 * 
	 * 如果以ZIP 文件發布, Activiti 會自動解壓ZIP中的文件
	 * @throws Exception
	 */
	
	@Test
	public void deployProcessByZIP() throws Exception {
		
		//3.使用服務對象相關方法完成操作
		//3.1 創建發布對象
		DeploymentBuilder deploymentBuilder = this.repositoryService.createDeployment();
		//3.2 指定發布相關文件(流程規則文件,圖片)
		//InputStream inputStreamBpm = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("\\diagrams\\LeaveFlow.zip");
		InputStream inputStreamBpm = this.getClass().getResourceAsStream("/diagrams/LeaveFlow.zip");
		//System.out.print(inputStreamBpm);
		ZipInputStream zipInputStream = new ZipInputStream(inputStreamBpm);
		
		deploymentBuilder.name("請假流程")
						.addZipInputStream(zipInputStream);
		//3.3 發布流程(使用deploy方法發布流程)
		deploymentBuilder.deploy();
	}
	
	/**
	 *  B.查看流程定義信息
	 * 		在項目中,我們主要使用 ProcessDefinition
	 *     
	 *      ProcessDefinition : 流程規則的整體信息(流程ID & 版本)
	 *      	ID: [key]:[version]:[隨機數]
	 *      	Name: 對應流程文件上process節點的Name屬性
	 *      	KEY: 對應流程文件上process節點的ID屬性
	 *      	Version: 發布時,找尋當前系統中對應key的最高版本,如果找到了,在最高版本加1,否則為1
	 *      ActivityImpl	: 描述當前規則下每一個活動相關訊息
	 * @throws Exception
	 */
	

	@Test
	public void queryProcessDefinition() throws Exception {
		//1.使用服務對象創建需要的查詢對象
	    ProcessDefinitionQuery processDefinitionQuery =	repositoryService.createProcessDefinitionQuery();
		//2.添加查詢參數
	    processDefinitionQuery
	    	//過慮條件
	    	.processDefinitionKey("LeaveFlow")
	    	//.processDefinitionName(arg0)
	    	//分頁條件
	    	//.listPage(arg0, arg1)
	    	//排序條件
	    	.orderByProcessDefinitionVersion().desc();
	    	   
		//3.調用查詢方法得到結果
	    List<ProcessDefinition> pdsList = processDefinitionQuery.list();	
		//4.List 結果
		for(int i=0; i < pdsList.size(); i++){
			ProcessDefinition pds = pdsList.get(i);
			
			ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl)repositoryService.getProcessDefinition(pds.getId());
						
			System.out.println("ID: " + pds.getId());
			System.out.println("Name: " + pds.getName());
			System.out.println("Key: " + pds.getKey());
			System.out.println("Version: " + pds.getVersion());
			System.out.println("Activities: " + pdImpl.getActivities());
			System.out.println("------------------------");
			
			
		}
		
	}
	
	
	// 	C.刪除流程規則
	@Test
	public void deleteProcess() throws Exception {
		String deplymentId = "1";
		//普通刪除 , 如果當前規則下有正在執行的流程, 則刪除失敗(保護)
		//repositoryService.deleteDeployment(deplymentId);
		//關聯刪除, 相對暴力, 會刪除正在執行的流程和流程執行的歷史
		repositoryService.deleteDeployment(deplymentId, true);
		
	}
	
	
	// 	D.查看流程附件(查看流程圖片)
	@Test
	public void displayProcessImage() throws Exception {
		// 1.獲取發布Id
		String deploymentId = "501";
		// 2.查找這次發布的所有資源文件名稱
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for(int i = 0;i < names.size(); i++){
			// 3.指定規則,獲取需要的文件名稱
			String name = names.get(i);
			//System.out.println("Name : "+ name);
			if(name.indexOf(".png") >= 0 ){
				imageName = name;
			}
		}
		System.out.println("imageName : "+ imageName);
		
		// 4.通過文件名稱去資料庫中查詢對應的輸入流
		if(imageName != null){
			InputStream inputStreamImageInputStream =	repositoryService.getResourceAsStream(deploymentId, imageName);
			// 5.把資料流寫到本地文件中
			File file = new File("D:/leave.png");
			FileUtils.copyInputStreamToFile(inputStreamImageInputStream, file);
		}

	}
	
	
}
