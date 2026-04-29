package com.cg.dto;

public class PostDto {
    
	private Long userId;
    private String content;
    
	public PostDto() {
	
	}
	public PostDto(Long userId, String content) {
		super();
		this.userId = userId;
		this.content = content;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
}