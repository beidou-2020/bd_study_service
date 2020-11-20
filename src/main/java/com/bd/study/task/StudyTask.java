package com.bd.study.task;

import com.alibaba.fastjson.JSONObject;
import com.bd.study.entitys.model.TZxzStudy;
import com.bd.study.service.ZxzStudyService;
import com.bd.study.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class StudyTask {
	
	@Resource
	private ZxzStudyService zxzStudyService;
	
	/**
	 * 定时更新学习计划的列表状态(每天凌晨1点执行)
	 */
	@Async(value = "taskExecutor")
	@Scheduled(cron = "0 0 1 * * ? ")				//正式环境：每天凌晨1点
	//@Scheduled(cron = "0 0/1 * * * ? ")			//本地环境：每分钟
	public void updatePlanStatus() {
		List<TZxzStudy> list = zxzStudyService.findAll();
		list.stream().forEach(item->{
			Integer planStatus = DateTimeUtils.datePositioning(new Date(), item.getPlanBegintime(), item.getPlanEndtime());
			if (!item.getPlanStatus().equals(planStatus)) {
				Integer result = zxzStudyService.updatePlanStatusById(item.getId(), planStatus);
				log.info("更新学习计划： {}, 的新状态为：{}，更新结果：{}",
						JSONObject.toJSONString(item), planStatus.intValue(), result.intValue());
			}
		});
	}

}
