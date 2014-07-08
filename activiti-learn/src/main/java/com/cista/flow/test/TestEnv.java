package com.cista.flow.test;

import static  org.junit.Assert.*;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestEnv {
	//搭建環境,  創建核心(ProcessEngine,檢測環境搭建是否正確
	@Test
	public void test() throws Exception {
		//創建流程引擎配置對象
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		
		//數據庫相關配置
		configuration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
		configuration.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:hank");
		configuration.setJdbcUsername("activiti");
		configuration.setJdbcPassword("activiti");
		
		//創建Table
		configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		//使用配置對象創建ProcessEngine
		ProcessEngine processEngine = configuration.buildProcessEngine();
		
	}
}
