package org.test.merchant.actions;

import java.io.IOException;
import java.util.Properties;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseActions {
	
	public RequestSpecification REQUEST;
	public Faker FAKER = new Faker();
	
	public BaseActions() {
		try {
			Properties props = new Properties();
			props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

			// Rest Assured config
			RestAssured.baseURI = props.getProperty("api.uri");
			RestAssured.port = Integer.valueOf(props.getProperty("api.port"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// basic request setting
		REQUEST = RestAssured.given().contentType(ContentType.JSON);
	}
}
