package com.cista.flow.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
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
			String processDefinitionKey = "LeaveFlow"; // 會抓最新版本
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
			System.out.println("Id:" + processInstance.getId() +" , ActivityId: " + processInstance.getActivityId());
		}
		// B.查看任務(私有任務, 公有任務)
		
		// C.認領任務
		// D.辦理認務
		// E.查看流程狀態
		
		
		
}
