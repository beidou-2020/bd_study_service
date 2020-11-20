package com.bd.study.entitys.enumerate;

/**
 * 个人计划状态值列表
 */
public enum PlanStatus {

	waiting_for_execution(0, "待执行"),
	executing(1, "执行中"),
	over(2, "已结束"),
	hang(3, "挂起");
	
	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	PlanStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	
	public static String msg(Integer code) {
        for (PlanStatus m : PlanStatus.values()) {
            if (m.getCode() == code) {
                return m.getMsg();
            }
        }
        return "未找到维护中的枚举值";
    }

}
