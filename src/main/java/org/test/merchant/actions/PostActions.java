package org.test.merchant.actions;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.test.merchant.model.Post;

public class PostActions extends BaseActions {

	public static String POSTS_ENDPOINT = "/posts";

	public Post addNewPost(Post request) {
		return given(REQUEST).body(request).post(POSTS_ENDPOINT).as(Post.class);
	}
	
	public List<Post> getPosts() {
		return given(REQUEST).get(POSTS_ENDPOINT).then().log().all().extract().body()
                .jsonPath().getList("", Post.class);
	}

	public Post getPost(String postId) {
		return given(REQUEST).pathParam("postId", postId).get(POSTS_ENDPOINT + "/{postId}").as(Post.class);
	}

	public void updatePost(Post post) {
		given(REQUEST).body(post).pathParam("postId", post.getId()).put(POSTS_ENDPOINT + "/{postId}");
	}

	public void deletePost(String postId) {
		given(REQUEST).pathParam("postId", postId).delete(POSTS_ENDPOINT + "/{postId}");
	}

	public void deletePost(Post post) {
		given(REQUEST).pathParam("postId", post.getId()).delete(POSTS_ENDPOINT + "/{postId}");
	}

	public boolean isPostExists(Post post) {
		return isPostExists(post.getId());
	}

	public boolean isPostExists(String postId) {
		return !given(REQUEST).pathParam("postId", postId).get(POSTS_ENDPOINT + "/{postId}").then().extract().body()
				.asString().equalsIgnoreCase("{}");
	}

}
