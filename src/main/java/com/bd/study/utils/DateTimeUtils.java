package com.bd.study.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class DateTimeUtils {

  public static void main(String[] args) {
	  Date now = new Date();
	  Date dateAfter = DateTimeUtils.getDateAfter(now, 7);
	  log.info("7天后的时间：{}", JSONObject.toJSONString(dateAfter));
  }

	/**
	 * 获取指定日期向后几天的日期值
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);//+后 -前
		return now.getTime();
	}


	/**
	 * 判断目标时间点在时间区间内、前、后
	 * @param targetDate
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Integer datePositioning(Date targetDate, Date beginDate, Date endDate) {
		if (Objects.isNull(targetDate) || Objects.isNull(beginDate) || Objects.isNull(endDate)) {
			return -1;
		}
		
		long targetTime = targetDate.getTime();
		long beginTime = beginDate.getTime();
		long endTime = endDate.getTime();
		
		if (targetTime >= beginTime && targetTime <= endTime) {
			// 执行中
			return 1;
		}
		
		if (targetTime > endTime) {
			// 已结束
			return 2;
		}
		
		// 待执行
		return 0;
	}

}
