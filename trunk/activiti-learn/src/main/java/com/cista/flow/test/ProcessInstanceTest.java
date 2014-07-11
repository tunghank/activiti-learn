package com.cista.flow.test;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


/**
 * 流程實例管理
 * 
 * 使用API的步驟
 * 1. 創建流程引擎ProcessEngine
 * 2. 獲取相關服務對象實例
 * 3. 使用服務對象相關方法完成流程操作
 * 
 * @author HANK
 *
 */

public class ProcessInstanceTest {
		// 1. 創建流程引擎ProcessEngine
		private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 2. 獲取相關服務對象實例
		private TaskService taskService = processEngine.getTaskService(); //任務相關服務對象,負責任務的查詢,辦理 
		private RuntimeService runtimeService = processEngine.getRuntimeService(); // 執行相關服務,負責流程的啟動,流轉等相關操作
		
		// 2. 創建倉庫服務對象
		private RepositoryService repositoryService = this.processEngine.getRepositoryService();
		
		@Test
		public void deployProcess() throws Exception {
			
		
			//3.使用服務對象相關方法完成操作
			//3.1 創建發布對象
			DeploymentBuilder deploymentBuilder = this.repositoryService.createDeployment();
			//3.2 指定發布相關文件(流程規則文件,圖片)
			//deploymentBuilder.addClasspathResource("\\diagrams\\HelloWorld.bpmn").addClasspathResource("\\diagrams\\HelloWorld.png");
			InputStream inputStreamBpm = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("\\diagrams\\LeaveFlow2.bpmn");
			InputStream inputStreamImage = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("\\diagrams\\LeaveFlow2.png");
			deploymentBuilder.name("請假流程")
							.addInputStream("LeaveFlow2.bpmn", inputStreamBpm)//添加Flow
							.addInputStream("LeaveFlow2.png", inputStreamImage);//添加圖片
			//3.3 發布流程(使用deploy方法發布流程)
			deploymentBuilder.deploy();
		}
		
		/**
		 *  A.啟動流程
		 *  
		 *  act_ru_task: 添加一條數據,描述人工參與的任務(主要屬性:"任務辦理者"和"任務分配的時間")
		 *  act_ru_execution: 添加一條數據, 只要是流程到這一個具體活動,都會在這張表添加數據
		 *  
		 * @throws Exception
		 */

		
		@Test
		public void startProcess() throws Exception {
			//按照指定流程ID啟動一個流程
			//String processDefinitionId = "LeaveFlow:4:704";
			//ProcessInstance processInstance =  runtimeService.startProcessInstanceById(processDefinitionId);
			
			//使用流程定義的Key啟動流程,使用這種方式永遠啟動的是當前key下版本最高的一個流程規則
			String processDefinitionKey = "LeaveFlow2"; // 會抓最新版本
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
			System.out.println("Id:" + processInstance.getId() +" , ActivityId: " + processInstance.getActivityId());
		}
		// B.查看任務(私有任務 assignee, 公有任務 candidate)
		
		// B.1私有任務
		@Test
		public void queryMyTask() throws Exception {
			String assignee = "Hank";
			//添加查詢參數
			List<Task> tasks = taskService.createTaskQuery()
						// 過濾條件
						.taskAssignee(assignee)
						//.taskDefinitionKey(arg0)
						//分頁
						//.listPage(fireResult, maxResults)
						//排序
						.orderByTaskCreateTime().desc()
						//執行查詢
						.list();
						//.singleResult();
			
			System.out.println("============[" + assignee + "] 私有任務==========================");
			for (Task task : tasks){
				System.out.println("Id: " + task.getId() + ", Name: " + task.getName() + 
								", Assignee: " + task.getAssignee() + ", CreateTime: " + task.getCreateTime());
			}
					
		}

		// B.1公有任務
		@Test
		public void queryCommonTask() throws Exception {
			String candidateUser = "User";
			//添加查詢參數
			List<Task> tasks = taskService.createTaskQuery()
						// 過濾條件
						.taskCandidateUser(candidateUser)
						//.taskDefinitionKey(arg0)
						//分頁
						//.listPage(fireResult, maxResults)
						//排序
						.orderByTaskCreateTime().desc()
						//執行查詢
						.list();
						//.singleResult();
			
			System.out.println("============[" + candidateUser + "] 可認領任務 ==========================");
			for (Task task : tasks){
				System.out.println("Id: " + task.getId() + ", Name: " + task.getName() + 
								", Assignee: " + task.getAssignee() + ", CreateTime: " + task.getCreateTime());
			}
					
		}
		// C.認領任務(針對公有任務需要此操作)
		@Test
		public void claimTask() throws Exception {
			String taskId = "1608";
			String userId = "Hank";
			
			taskService.claim(taskId, userId);
							
		}
		
		// D.辦理認務
		// D.1 
		@Test
		public void completeMyTask() throws Exception {
			String taskId = "1104";
			taskService.complete(taskId);

					
		}
		
		// E.查看流程狀態
		
		@Test
		public void queryTask() throws Exception {
			String taskId = "1608";
			taskService.complete(taskId);

					
		}
		
}
