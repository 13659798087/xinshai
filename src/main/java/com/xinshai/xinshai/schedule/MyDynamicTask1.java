/*
package com.xinshai.xinshai.schedule;

import com.xinshai.xinshai.services.MessageServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

*/
/**
 * 实现动态配置推送时间
*//*


@Component
public class MyDynamicTask1 implements SchedulingConfigurer {

    @Resource
    private MessageServices messageServices;

    private static Logger log = LoggerFactory.getLogger(MyDynamicTask1.class);

    public static String cron;

    public static String cron2;

    @Value("${dynamicTask}")
    private String dynamicTask;

    @Value("${dynamicTask2}")
    private String dynamicTask2;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(doTask(), getTrigger());
        scheduledTaskRegistrar.addTriggerTask(doTask2(),getTrigger2());
    }

    private Runnable doTask() {
        return new Runnable() {
            @Override
            public void run() {
                // 业务逻辑
                log.info("每个1分钟-执行了MyDynamicTask,时间为:" + new Date(System.currentTimeMillis()));
            }
        };
    }
    private Trigger getTrigger() {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 触发器
                CronTrigger trigger = new CronTrigger(getCron());
                return trigger.nextExecutionTime(triggerContext);
            }
        };
    }
    public String getCron(){
        String newCron = messageServices.getCorn(dynamicTask);
        if (StringUtils.isEmpty(newCron)) {
            try {
                throw new Exception("The config cron expression is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!newCron.equals(cron)) {
            log.info(new StringBuffer("1111111111Cron has been changed to:'").append(newCron).append("'. Old cron was:'").append(cron).append("'").toString());
            cron = newCron;
        }
        return cron;
    }



    private Runnable doTask2() {
        return new Runnable() {
            @Override
            public void run() {
                // 业务逻辑
                log.info("每个2分钟--执行了MyDynamicTask2,时间为:" + new Date(System.currentTimeMillis()));
            }
        };
    }
    private Trigger getTrigger2() {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 触发器
                CronTrigger trigger = new CronTrigger(getCron2());
                return trigger.nextExecutionTime(triggerContext);
            }
        };
    }
    public String getCron2(){
        String newCron = messageServices.getCorn(dynamicTask2);
        if (StringUtils.isEmpty(newCron)) {
            try {
                throw new Exception("2222222The config cron2 expression is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!newCron.equals(cron2)) {
            log.info(new StringBuffer("22222222Cron2 has been changed to:'").append(newCron).append("'. Old cron2 was:'").append(cron2).append("'").toString());
            cron2 = newCron;
        }
        return cron2;
    }

}
*/
