package com.cg.dto;

import java.sql.Timestamp;

public class PostResponseDto {

	    private Long postId;
	    private String content;
	    private Timestamp timestamp;
	    private Long userId;
	    private String username;
		
	    public PostResponseDto() {
		}

		public PostResponseDto(Long postId, String content, Timestamp timestamp, Long userId, String username) {
			super();
			this.postId = postId;
			this.content = content;
			this.timestamp = timestamp;
			this.userId = userId;
			this.username = username;
		}

		public Long getPostId() {
			return postId;
		}

		public void setPostId(Long postId) {
			this.postId = postId;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
	}


