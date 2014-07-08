package com.cista.flow.test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

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
	
// 	B.查看流程定義信息
// 	C.刪除流程規則
// 	D.查看流程附件(查看流程圖片)
	
	
}
