
package com.cg.dto;

public class SuccessMessageDto {

	private String msg;
	
	private Long id;

	public SuccessMessageDto(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SuccessMessageDto(String msg, Long id) {
		super();
		this.msg = msg;
		this.id = id;
	}
	
	
	
	
}
