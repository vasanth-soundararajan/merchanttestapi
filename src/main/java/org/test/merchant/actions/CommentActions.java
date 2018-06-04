package org.test.merchant.actions;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.test.merchant.model.Comment;

public class CommentActions extends BaseActions {

	public static String COMMENT_ENDPOINT = "/comments";

	public Comment addNewComment(Comment request) {
		return given(REQUEST).body(request).post(COMMENT_ENDPOINT).as(Comment.class);
	}

	public Comment getComment(String commentId) {
		return given(REQUEST).pathParam("commentId", commentId).get(COMMENT_ENDPOINT + "/{commentId}")
				.as(Comment.class);
	}
	
	public List<Comment> getComments() {
		return given(REQUEST).get(COMMENT_ENDPOINT).then().log().all().extract().body()
                .jsonPath().getList("", Comment.class);
	}

	public void updateComment(Comment comment) {
		given(REQUEST).body(comment).pathParam("commentId", comment.getId()).put(COMMENT_ENDPOINT + "/{commentId}");
	}

	public void deleteComment(String commentId) {
		given(REQUEST).pathParam("commentId", commentId).delete(COMMENT_ENDPOINT + "/{commentId}");
	}

	public void deleteComment(Comment comment) {
		given(REQUEST).pathParam("commentId", comment.getId()).delete(COMMENT_ENDPOINT + "/{commentId}");
	}

	public boolean isCommentExists(Comment comment) {
		return isCommentExists(comment.getId());
	}

	public boolean isCommentExists(String commentId) {
		return !given(REQUEST).pathParam("commentId", commentId).get(COMMENT_ENDPOINT + "/{commentId}").then().extract()
				.body().asString().equalsIgnoreCase("{}");
	}

}
