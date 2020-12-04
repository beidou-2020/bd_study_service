package com.bd.study.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.study.entitys.dto.AddStudyParam;
import com.bd.study.entitys.dto.PageParam;
import com.bd.study.entitys.dto.UpdateStudyParam;
import com.bd.study.entitys.enumerate.PlanStatus;
import com.bd.study.entitys.model.TZxzStudy;
import com.bd.study.entitys.query.StudyQuery;
import com.bd.study.repository.TZxzStudyMapper;
import com.bd.study.service.ZxzStudyService;
import com.bd.study.utils.BeanUtil;
import com.bd.study.utils.DateTimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ZxzStudyServiceImpl implements ZxzStudyService {
	
	@Resource
	private TZxzStudyMapper tZxzStudyMapper;

	@Override
	public List<TZxzStudy> findByCurrentMonth() {
		return tZxzStudyMapper.findByCurrentMonth();
	}

	@Override
	public Integer countStudyNumber() {
		return tZxzStudyMapper.countStudyNumber();
	}

	@Override
	public PageInfo<TZxzStudy> pageFindByQuery(StudyQuery queryStudy, PageParam pageQuery) {
		PageHelper.startPage(pageQuery.getCurrentPageNumber(), pageQuery.getPageSize());
		TZxzStudy study = new TZxzStudy();
		BeanUtil.copyProperties(queryStudy, study);
		List<TZxzStudy> list = tZxzStudyMapper.findByQuery(study);
		PageInfo<TZxzStudy> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TZxzStudy deleteStudyPlan(Long id) {
		TZxzStudy study = tZxzStudyMapper.selectByPrimaryKey(id);
		if (Objects.nonNull(study)){
			// 获取当前登录的用户ID
			long currLoginUserId = 1l;
			tZxzStudyMapper.deleteStudyPlan(id, currLoginUserId);
			return study;
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TZxzStudy addStudyInfo(AddStudyParam param) {
		TZxzStudy study = new TZxzStudy();
		BeanUtil.copyProperties(param, study);
		try{
			// 按照业务需求处理日期参数
			study.setPlanBegintime(DateTimeUtils.removeDateHms(param.getPlanBegintime().getTime()));
			study.setPlanEndtime(DateTimeUtils.removeDateHms(param.getPlanEndtime().getTime()));
		}catch (Exception ex){
			log.error("添加学习计划时开始和结束时间参数处理异常，param:{}", JSONObject.toJSONString(param), ex);
		}
		tZxzStudyMapper.insertSelective(study);
		log.info("插入成功后的数据主键为：{}", study.getId());
		return study;
	}

	@Override
	public TZxzStudy findById(Long id) {
		return tZxzStudyMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public TZxzStudy updateStudyById(UpdateStudyParam param) {
		TZxzStudy study = new TZxzStudy();
		BeanUtil.copyProperties(param, study);
		try{
			// 按照业务需求处理日期参数
			study.setPlanBegintime(DateTimeUtils.removeDateHms(param.getPlanBegintime().getTime()));
			study.setPlanEndtime(DateTimeUtils.removeDateHms(param.getPlanEndtime().getTime()));
		}catch (Exception ex){
			log.error("添加学习计划时开始和结束时间参数处理异常，param:{}", JSONObject.toJSONString(param), ex);
		}
		tZxzStudyMapper.updateByPrimaryKeySelective(study);
		return study;
	}

	@Override
	public List<TZxzStudy> findAll() {
		TZxzStudy study = new TZxzStudy();
		study.setValidMark(1);
		return tZxzStudyMapper.findByQuery(study);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updatePlanStatusById(Long id, Integer planStatus) {
		return tZxzStudyMapper.updatePlanStatusById(id, planStatus);
	}

	@Override
	public List<TZxzStudy> findAllByQuery(TZxzStudy study) {
		return tZxzStudyMapper.findByQuery(study);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer batchInsert(List<TZxzStudy> list) {
		if (CollectionUtils.isEmpty(list)) {
			return 0;
		}
		return tZxzStudyMapper.batchInsert(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void syncTaskStatus() {
		TZxzStudy study = new TZxzStudy();
		study.setValidMark(1);
		List<TZxzStudy> list = tZxzStudyMapper.findByQuery(study);
		// 不同步挂起和结束的计划
		List<TZxzStudy> dealWithStudyList = list.stream().filter(item -> PlanStatus.hang.getCode() != item.getPlanStatus()
				&& PlanStatus.over.getCode() != item.getPlanStatus()).collect(Collectors.toList());

		dealWithStudyList.stream().forEach(item->{
			Integer planStatus = DateTimeUtils.datePositioning(new Date(), item.getPlanBegintime(), item.getPlanEndtime());
			if (!item.getPlanStatus().equals(planStatus)) {
				Integer result = this.updatePlanStatusById(item.getId(), planStatus);
				log.info("更新学习计划： {}, 的新状态为：{}，更新结果：{}",
						JSONObject.toJSONString(item), planStatus.intValue(), result.intValue());
			}
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer hangStudy(Long id) {
		return tZxzStudyMapper.hangStudy(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer trunOnStudy(Long id) {
		// 获取正在执行中的计划中，最晚结束的那一条记录
		TZxzStudy referenceStudyInfo = tZxzStudyMapper.findExecutingStudyOrderByEndtimeDesc();
		TZxzStudy study = new TZxzStudy();
		study.setId(id);
		study.setPlanStatus(PlanStatus.executing.getCode());
		if (Objects.isNull(referenceStudyInfo)){
			// 重新设置默认的计划开始时间和计划结束时间
			Date nowDate = new Date();
			study.setPlanBegintime(nowDate);
			Date endTime = DateTimeUtils.getDateAfter(nowDate, 7);
			study.setPlanEndtime(endTime);
		}else {
			study.setPlanBegintime(referenceStudyInfo.getPlanBegintime());
			study.setPlanEndtime(referenceStudyInfo.getPlanEndtime());
		}

		return tZxzStudyMapper.updateByPrimaryKeySelective(study);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer batchDelete(String idListStr) {
		// 参数转化
		List<Long> idList = Arrays.stream(idListStr.split(",")).
				map(id -> Long.parseLong(id)).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(idList)){
			return 0;
		}

		// 获取当前登录的用户ID
		long currLoginUserId = 1l;
		return tZxzStudyMapper.batchDelete(idList, currLoginUserId);
	}
}
