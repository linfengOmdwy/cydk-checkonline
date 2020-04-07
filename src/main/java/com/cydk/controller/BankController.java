package com.cydk.controller;

import com.cydk.entity.Msg;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class BankController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 获取待办任务列表
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "/bank/getCreditInfoList")
    @ResponseBody
    public Msg  getCreditInfoList(@RequestParam("userAccount")String userAccount){
        List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned(userAccount).taskDefinitionKey("bankCheck").list();
        ArrayList<Object> taskListWithStartUserId = new ArrayList<>();
        for (Task task : taskList) {
            HashMap<String, Object> map = new HashMap<>();
            String processInstanceId = task.getProcessInstanceId();
            String startUserId=(String)runtimeService.getVariable(processInstanceId,"applyUserId");
            map.put("taskId",task.getId());
            map.put("startUserId",startUserId);
            taskListWithStartUserId.add(map);
        }
        return Msg.success().add("taskListWithStartUserId",taskListWithStartUserId);
    }
    /**
     * 同意申请
     */
    @RequestMapping(value = "/bank/agree")
    @ResponseBody
    public Msg Agree(@RequestParam("taskId")String taskId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("bankAgree","true");
        taskService.complete(taskId,map);
        return Msg.success();
    }
    /**
     * 拒绝申请
     */
    @RequestMapping(value = "/bank/reject")
    @ResponseBody
    public Msg reject(@RequestParam("taskId")String taskId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("bankAgree","false");
        taskService.complete(taskId,map);
        return Msg.success();
    }
}
