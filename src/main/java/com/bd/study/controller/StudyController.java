package com.bd.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd.study.controller.common.Result;
import com.bd.study.entitys.dto.AddStudyParam;
import com.bd.study.entitys.dto.ImportStudyParam;
import com.bd.study.entitys.dto.PageParam;
import com.bd.study.entitys.dto.UpdateStudyParam;
import com.bd.study.entitys.enumerate.ResultCode;
import com.bd.study.entitys.model.TZxzStudy;
import com.bd.study.entitys.query.StudyQuery;
import com.bd.study.service.ZxzStudyService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/study")
@Slf4j
public class StudyController {
	
	@Resource
	private ZxzStudyService zxzStudyService;

	
	/**
	 * 学习计划列表
	 * @param queryStudy
	 * @param pageQuery
	 * @return
	 */
	@GetMapping(value = "/list", produces = "application/json;charset=utf-8")
	@ResponseBody
	public PageInfo<TZxzStudy> list(StudyQuery queryStudy, @Valid PageParam pageQuery) {
		PageInfo<TZxzStudy> pageInfo = zxzStudyService.pageFindByQuery(queryStudy, pageQuery);
		return pageInfo;
	}


	/**
	 * 删除学习计划
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/delete/{id}", produces = "application/json;charset=utf-8")
	public TZxzStudy deleteById(@PathVariable(name = "id") Long id) {
		TZxzStudy result = zxzStudyService.deleteStudyPlan(id);
		return result;
	}
	

	/**
	 * 添加学习计划
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/add")
	@ResponseBody
	public TZxzStudy add(@RequestBody @Valid AddStudyParam param) {
		TZxzStudy result = zxzStudyService.addStudyInfo(param);
		return result;
	}

	
	/**
	 * 更新学习计划信息
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/update")
	@ResponseBody
	public TZxzStudy updateStudyInfo(@RequestBody @Valid UpdateStudyParam param) {
		TZxzStudy study = zxzStudyService.updateStudyById(param);
		return study;
	}
	
	/**
	 * 查看学习计划详情信息
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/studyDetails/{id}", produces = "application/json;charset=utf-8")
	public TZxzStudy studyDetails(@PathVariable(name = "id") Long id) {
		TZxzStudy study = zxzStudyService.findById(id);
		return study;
	}

	/**
	 * 批量插入学习计划
	 * @param param
	 * @return
	 */
	@PostMapping("/batchInsert")
	public String batchInsert(@RequestBody @Valid ImportStudyParam param){
		Integer result = zxzStudyService.batchInsert(param.getList());
		return JSONObject.toJSONString(result);
	}

	/**
	 * 手动同步各项计划的执行状态(忽略已经挂起的计划)
	 * @return
	 */
	@GetMapping("/syncTaskStatus")
	@ResponseBody
	public Result syncTaskStatus(){
		zxzStudyService.syncTaskStatus();
		return Result.ok("计划状态同步成功");
	}

	/**
	 * 获取当月计划结束的学习计划内容(执行中的)
	 * @return
	 */
	@GetMapping("/endStudyByCurrentMonth")
	@ResponseBody
	public Result endStudyByCurrentMonth(){
		List<TZxzStudy> list = zxzStudyService.findByCurrentMonth();
		return Result.ok(list);
	}

	/**
	 * 获取累计计划项
	 * @return
	 */
	@GetMapping("/countStudyNumber")
	@ResponseBody
	public Result countStudyNumber(){
		Integer count = zxzStudyService.countStudyNumber();
		return Result.ok(count);
	}


	/**
	 * 挂起计划
	 */
	@PostMapping("/hang/{id}")
	@ResponseBody
	public Result hangStudy(@PathVariable(name = "id") Long id){
		Integer result = zxzStudyService.hangStudy(id);
		return Result.ok(result);
	}

	/**
	 * 重启计划
	 */
	@PostMapping("/trunOn/{id}")
	@ResponseBody
	public Result trunOnStudy(@PathVariable(name = "id") Long id){
		Integer result = zxzStudyService.trunOnStudy(id);
		return Result.ok(result);
	}

	/**
	 * 批量删除计划
	 * @param idListStr
	 * @return
	 */
	@PostMapping("/batchDelete")
	@ResponseBody
	public Result batchDelete(@RequestParam(name = "idListStr") String idListStr){
		// 参数不能为空
		if (StringUtils.isEmpty(idListStr)){
			return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
		}
		Integer resultData = zxzStudyService.batchDelete(idListStr);
		return Result.ok(resultData);
	}
	
	
	
	
	
	
	
	
	
	
}
