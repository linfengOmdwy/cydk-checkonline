package com.cydk.controller;

import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class ActivitiConfiguration {

    @javax.annotation.Resource
    private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(){
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        spec.setDataSource(dataSource);
        spec.setTransactionManager(platformTransactionManager);
        spec.setDatabaseSchemaUpdate("true");
        Resource[] resources = null;
        // 启动自动部署流程
        try {
            resources = new PathMatchingResourcePatternResolver().getResources("classpath*:bpmn/*.bpmn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        spec.setDeploymentResources(resources);
        return spec;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine(){
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }


    @Bean
    public RepositoryService repositoryService() throws Exception{
        return processEngine().getObject().getRepositoryService();
    }
    @Bean
    public RuntimeService runtimeService() throws Exception{
        return processEngine().getObject().getRuntimeService();
    }
    @Bean
    public TaskService taskService() throws Exception{
        return processEngine().getObject().getTaskService();
    }
    @Bean
    public HistoryService historyService() throws Exception{
        return processEngine().getObject().getHistoryService();
    }
    @Bean
    public IdentityService identityService() throws Exception{
        return processEngine().getObject().getIdentityService();
    }

    @Bean
    public ManagementService managementService() throws Exception{
        return processEngine().getObject().getManagementService();
    }

}