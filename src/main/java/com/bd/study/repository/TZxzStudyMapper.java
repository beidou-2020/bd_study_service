package com.bd.study.repository;

import com.bd.study.entitys.model.TZxzStudy;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TZxzStudyMapper {
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
     * 复合条件查询学习计划列表数据
     * @param study
     * @return
     */
    List<TZxzStudy> findByQuery(TZxzStudy study);
    
    /**
     * 逻辑删除学习计划，计划默认终止
     * @param id
     * @return
     */
    Integer deleteStudyPlan(@Param("id") Long id);
    
    int deleteByPrimaryKey(Long id);

    int insert(TZxzStudy record);

    /**
     * 添加学习计划
     * @param record
     * @return
     */
    int insertSelective(TZxzStudy record);
    
    /**
     * 批量插入学习计划
     * @param list
     * @return
     */
    Integer batchInsert(@Param("list") List<TZxzStudy> list);

    /**
     * 根据主键查询学习计划 
     * @param id
     * @return
     */
    TZxzStudy selectByPrimaryKey(Long id);

    /**
     * 根据主键修改学习计划信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TZxzStudy record);
    
    /**
     * 更新学习计划状态
     * @param id
     * @param planStatus
     * @return
     */
    int updatePlanStatusById(@Param("id") Long id, @Param("planStatus") Integer planStatus);

    int updateByPrimaryKey(TZxzStudy record);

    /**
     * 挂起计划
     * @param id
     * @return
     */
    Integer hangStudy(@Param("id") Long id);

    /**
     * 获取正在执行中的计划中，最晚结束的那一条记录
     * @return
     */
    TZxzStudy findExecutingStudyOrderByEndtimeDesc();
    
}