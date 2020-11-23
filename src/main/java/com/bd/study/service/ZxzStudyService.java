package com.bd.study.service;

import com.bd.study.entitys.dto.AddStudyParam;
import com.bd.study.entitys.dto.PageParam;
import com.bd.study.entitys.dto.UpdateStudyParam;
import com.bd.study.entitys.model.TZxzStudy;
import com.bd.study.entitys.query.StudyQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ZxzStudyService {
	
	/**
     * 获取当月计划结束的学习计划内容数据(执行中的)
     * @return
     */
    List<TZxzStudy> findByCurrentMonth();

    /**
     * 累计计划项
     * @return
     */
    Integer countStudyNumber();
    
    /**
     * 复合查询计划列表
     * @param study
     * @return
     */
    List<TZxzStudy> findAllByQuery(TZxzStudy study);
    
    /**
     * 分页查询
     * @param queryStudy
     * @param pageQuery
     * @return
     */
    PageInfo<TZxzStudy> pageFindByQuery(StudyQuery queryStudy, PageParam pageQuery);
    
    /**
     * 逻辑删除学习计划，计划默认终止
     * @param id
     * @return
     */
    TZxzStudy deleteStudyPlan(Long id);
    
    /**
     * 添加学习计划
     * @param param
     * @return
     */
    TZxzStudy addStudyInfo(AddStudyParam param);
    
    /**
     * 批量插入学习计划
     * @param list
     * @return
     */
    Integer batchInsert(List<TZxzStudy> list);
    
    /**
     * 根据主键获取学习计划
     * @param id
     * @return
     */
    TZxzStudy findById(Long id);
    
    /**
     * 根据主键修改学习计划
     * @param param
     * @return
     */
    TZxzStudy updateStudyById(UpdateStudyParam param);
    
    /**
     * 获取所有的学习计划
     * @return
     */
    List<TZxzStudy> findAll();
    
    /**
     * 更新学习计划状态
     * @return
     */
    Integer updatePlanStatusById(Long id, Integer planStatus);

    /**
     * 手动同步各项计划的执行状态
     */
    void syncTaskStatus();

    /**
     * 挂起计划
     * @return
     */
    Integer hangStudy(Long id);

    /**
     * 重启计划
     * @param id
     * @return
     */
    Integer trunOnStudy(Long id);

    /**
     * 批量删除计划(逻辑删除)
     * @return
     */
    Integer batchDelete(String idListStr);

}
