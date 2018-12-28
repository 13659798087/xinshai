/*
package com.xinshai.xinshai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


 */
/***Spring Boot 定时任务单线程和多线程
 * 要实现多线程  MyDynamicTask类
 * MyDynamicTask类实现SchedulingConfigurer接口，重写configureTasks方法
 * 这个方法显式的设置一个ScheduledExecutorService就可以达到并发的效果了。
 * 我们要做的仅仅是实现SchedulingConfigurer接口，重写configureTasks方法
 *  //设定一个长度10的定时任务线程池
 *  scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(2));
 *  如果不执行上面方法，多个定时任务就只是单线程*//*




@Component
public class ScheduledTask2 {

	private Logger logger = LoggerFactory.getLogger(ScheduledTask2.class);

	// cron接受cron表达式，根据cron表达式确定定时规则
	@Scheduled(cron="0/5 * * * * ? ")   //每5秒执行一次
	public void testCron() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(sdf.format(new Date())+"*********每5秒执行一次");
	}

	// cron接受cron表达式，根据cron表达式确定定时规则
	@Scheduled(cron="0/1 * * * * ? ")   //每5秒执行一次
	public void testCron1() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(sdf.format(new Date())+"*********每1秒执行一次");
	}

	@Scheduled(cron="0/2 * * * * ? ")   //每5秒执行一次
	public void testCron2() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(sdf.format(new Date())+"*********每2秒执行一次");
	}
}
*/
