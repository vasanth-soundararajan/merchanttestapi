package merchanttestapi;

import org.test.merchant.actions.PostActions;
import org.test.merchant.config.TestBase;
import org.test.merchant.model.Post;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PostsTest extends TestBase {

	PostActions postActions;
	Post createdPost;

	@BeforeClass
	public void beforeClass() {
		postActions = new PostActions();
		Post post = new Post();
		post.setUserId("22");
		post.setTitle(FAKER.crypto().md5());
		post.setBody(FAKER.lorem().sentence(20));
		createdPost = postActions.addNewPost(post);
	}

	@Test(priority=1)
	public void shouldHaveStatus200ForAllPostsList() {
		REQUEST.get("/posts").then().statusCode(200);
	}
	
	@Test(priority=2)
	public void getPostsTest() {
		Post post = postActions.getPost(createdPost.getId());
		Assert.assertTrue(post.getBody().equals(createdPost.getBody()));
	}
	
	@Test(priority=3)
	public void updatePostsTest() {
		Post post = postActions.getPost(createdPost.getId());
		post.setBody(FAKER.lorem().sentence(20));
		Post updatedpost = postActions.getPost(createdPost.getId());
		Assert.assertTrue(post.getBody().equals(updatedpost.getBody()));
	}

	@Test(priority=4)
	public void deletePostsTest() {
		postActions.deletePost(createdPost);
		Assert.assertFalse(postActions.isPostExists(createdPost));
	}

}
