package com.bd.study.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class DateTimeUtils {

	private static final String ymd = "yyyy-MM-dd";

  public static void main(String[] args) throws Exception{

  }


	/**
	 * 格式化时间：返回年月日
	 * @param date
	 * @return
	 * @throws Exception
	 */
  public static Date removeDateHms(Long date) throws Exception {
	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ymd);
	  String format = simpleDateFormat.format(new Date(date));
	  Date result = simpleDateFormat.parse(format);
	  return result;
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
