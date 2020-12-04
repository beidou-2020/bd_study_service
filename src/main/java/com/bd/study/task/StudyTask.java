package com.bd.study.task;

import com.bd.study.service.ZxzStudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class StudyTask {
	
	@Resource
	private ZxzStudyService zxzStudyService;
	
	/**
	 * 定时更新学习计划的列表状态(每天凌晨1点执行)
	 */
	@Async(value = "taskExecutor")
	//@Scheduled(cron = "0 0 1 * * ? ")				//正式环境：每天凌晨1点
	@Scheduled(cron = "0 0/1 * * * ? ")				//本地环境：每分钟
	public void updatePlanStatus() {
		log.info("同步计划状态===begin");
		zxzStudyService.syncTaskStatus();
		log.info("同步计划状态===end");
	}

}
