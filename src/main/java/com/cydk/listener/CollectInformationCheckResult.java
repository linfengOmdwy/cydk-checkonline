package com.cydk.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("CollectInformationCheckResult")
public class CollectInformationCheckResult implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        //获取区就业的审批意见
        String districtJiuYeAgree =(String)delegateExecution.getVariable("districtJiuYeAgree");
        //获取金融机构的审批意见
        String bankAgree =(String)delegateExecution.getVariable("bankAgree");
        //设定控制流程走向的参数
        if ("true".equals(districtJiuYeAgree)&&"true".equals(bankAgree)){
            delegateExecution.setVariable("GuaranteeAndCreditApproved","true");
        }else {
            delegateExecution.setVariable("GuaranteeAndCreditApproved","false");
        }
    }
}

