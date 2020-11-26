package com.bd.study.entitys.query;

import lombok.Data;
import lombok.ToString;
import java.util.Date;

@Data
@ToString
public class StudyQuery {
	
	/**
     * 主题
     */
    private String title;

    /**
     * 学习项
     */
    private String items;
    
    /**
     * 计划开始时间
     */
    private Date planBegintime;

    /**
     * 计划结束时间
     */
    private Date planEndtime;

    /**
     * 默认查询未删除的
     */
    private Integer validMark = 1;
}
