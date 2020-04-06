package com.cydk.controller;

import com.cydk.entity.Msg;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class EntrepreneurController {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * 启动流程
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "/Entrepreneur/startProcess")
    @ResponseBody
    public Msg startProcess(@RequestParam("userAccount")String userAccount){
        identityService.setAuthenticatedUserId(userAccount);
        ProcessInstance egApproval = runtimeService.startProcessInstanceByKey("EGApproval");
        if (egApproval==null){
            return Msg.fail();
        }else {
            return Msg.success();
        }
    }

    //完成任务
    @RequestMapping(value = "/Entrepreneur/completeTask")
    @ResponseBody
    public Msg completeTask(@RequestParam("applicantType")String applicantType,
                             @RequestParam("userAccount") String userAccount){
        //设定流程变量
        HashMap<String, Object> map = new HashMap<>();
        map.put("applicantType",applicantType);
        //查询当前用户的待办任务
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userAccount).singleResult();
        taskService.complete(task.getId(),map);
        return Msg.success();
    }
}
