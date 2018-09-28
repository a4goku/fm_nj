package com.ufgov.sssfm.project.monitor.job.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("fanTask")
public class FanTask
{

    public void fanParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void fanNoParams()
    {
        System.out.println("执行无参方法");
    }

}
