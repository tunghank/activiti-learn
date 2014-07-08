package com.cista.flow.test;

import static  org.junit.Assert.*;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;
import org.junit.Test;
import java.util.List;


public class HelloWorld {
	//搭建環境,  創建核心(ProcessEngine,檢測環境搭建是否正確
	//最簡單方式獲取processEngine 實例, 自動去加載classpath下名為activiti.cfg.xml配置文件
	//ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 使用Activiti 相關API 步驟
	 *  1.創建核心流程引擎對象ProcessEngine
	 *  2.使用ProcessEngine獲取需要的服務對象
	 *  3.使用服務對象鄉關方法完成操作
	 * @throws Exception
	 */
		
	//1. 發布流程
	@Test
	public void deployFlow() throws Exception {
		//最簡單方式獲取processEngine
		//1.創建核心流程引擎對象ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		
		//2.使用ProcessEngine獲取需要的服務對象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		//3.使用服務對象鄉關方法完成操作
		//3.1 創建發布對象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		//3.2 指定發布相關文件
		deploymentBuilder.addClasspathResource("\\diagrams\\HelloWorld.bpmn").addClasspathResource("\\diagrams\\HelloWorld.png");
		//3.3 發布流程
		deploymentBuilder.deploy();
	}
	
	//2. 啟動流程實例
	@Test
	public void startFlow() throws Exception {
		//最簡單方式獲取processEngine
		//1.創建核心流程引擎對象ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		
		//2.創建運行時服務對象
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		//3.使用服務
		runtimeService.startProcessInstanceByKey("HelloWorld");
	}
	
	//3. 查看任務
	@Test
	public void queryTask() throws Exception {
		//最簡單方式獲取processEngine
		//1.創建核心流程引擎對象ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		
		//2.得到任務
		TaskService taskService = processEngine.getTaskService();
		
		//3.使用服務
		String userId = "Hank";
		List<Task> tasks = taskService.createTaskQuery() // 創建查詢對象
			.taskAssignee(userId) //加入查詢條件
			.list(); //List
		
		for(int i=0; i < tasks.size(); i ++){
			Task task = tasks.get(i);
			System.out.println("ID: " + task.getId());
			System.out.println("Name: " + task.getName());
			System.out.println("Assignee: " + task.getAssignee());
			System.out.println("CreateTime: " + task.getCreateTime());
		}
	}
	
	//4. 辦理任務
	@Test
	public void completeTask() throws Exception {
		//最簡單方式獲取processEngine
		//1.創建核心流程引擎對象ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		
		//2.得到任務
		TaskService taskService = processEngine.getTaskService();
		
		//3.使用服務, 完成服務(提交服務)
		
		
		String userId = "Hank";
		List<Task> tasks = taskService.createTaskQuery() // 創建查詢對象
			.taskAssignee(userId) //加入查詢條件
			.list(); //List
		
		for(int i=0; i < tasks.size(); i ++){
			Task task = tasks.get(i);
			System.out.println("ID: " + task.getId());
			System.out.println("Name: " + task.getName());
			System.out.println("Assignee: " + task.getAssignee());
			System.out.println("CreateTime: " + task.getCreateTime());
		}
		String taskId = "202";
		taskService.complete(taskId);
	}
}
