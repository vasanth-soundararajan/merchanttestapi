package org.test.merchant.actions;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.test.merchant.model.User;

public class UserActions extends BaseActions {

	public static String USERS_ENDPOINT = "/users";

	public User addNewUser(User request) {
		return given(REQUEST).body(request).post(USERS_ENDPOINT).as(User.class);
	}

	public User getUser(String userId) {
		return given(REQUEST).pathParam("userId", userId).get(USERS_ENDPOINT + "/{userId}").as(User.class);
	}
	
	public List<User> getUsers() {
		return given(REQUEST).get(USERS_ENDPOINT + "/{userId}").then().log().all().extract().body()
                .jsonPath().getList("", User.class);
	}

	public void updateUser(User user) {
		given(REQUEST).body(user).pathParam("userId", user.getId()).put(USERS_ENDPOINT + "/{userId}");
	}

	public void deleteUser(int userId) {
		given(REQUEST).pathParam("userId", userId).delete(USERS_ENDPOINT + "/{userId}");
	}

	public void deleteUser(User user) {
		given(REQUEST).pathParam("userId", user.getId()).delete(USERS_ENDPOINT + "/{userId}");
	}

	public boolean isUserExists(User user) {
		return isUserExists(user.getId());
	}

	public boolean isUserExists(String userId) {
		return !given(REQUEST).pathParam("userId", userId).get(USERS_ENDPOINT + "/{userId}").then().extract().body()
				.asString().equalsIgnoreCase("{}");
	}

}
