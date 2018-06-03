package org.test.merchant.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {
	
	@JsonProperty
	private int id;
	@JsonProperty
	private String title;
	@JsonProperty
	private String body;
	@JsonProperty
	private String userId;

}
