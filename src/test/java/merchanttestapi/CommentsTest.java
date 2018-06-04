package merchanttestapi;

import java.util.List;

import org.test.merchant.actions.CommentActions;
import org.test.merchant.actions.PostActions;
import org.test.merchant.actions.UserActions;
import org.test.merchant.config.TestBase;
import org.test.merchant.model.Address;
import org.test.merchant.model.Comment;
import org.test.merchant.model.Geo;
import org.test.merchant.model.Post;
import org.test.merchant.model.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CommentsTest extends TestBase {

	CommentActions commentActions;
	PostActions postActions;
	UserActions userActions;
	Post createdPost;
	User createdUser;
	Comment createdComment;

	@BeforeClass
	public void beforeClass() {	
		commentActions =  new CommentActions();
		postActions = new PostActions();
		userActions = new UserActions();
		
		Geo geo =  new Geo();
		geo.setLat(FAKER.address().latitude());
		geo.setLng(FAKER.address().longitude());
		
		Address address =  new Address();
		address.setStreet(FAKER.address().streetAddress());
		address.setCity(FAKER.address().city());
		address.setZipcode(FAKER.address().zipCode());
		address.setGeo(geo);
		
		User user = new User();
		user.setName(FAKER.name().fullName());
		user.setUsername(FAKER.crypto().md5());
		user.setEmail(FAKER.internet().emailAddress());
		user.setAddress(address);
		
		createdUser = userActions.addNewUser(user);
		
		Post post = new Post();
		post.setUserId(createdUser.getId());
		post.setTitle(FAKER.crypto().md5());
		post.setBody(FAKER.lorem().sentence(20));
		createdPost = postActions.addNewPost(post);
		
	}

	@Test(priority=1)
	public void shouldHaveStatus200ForAllCommentsList() {
		REQUEST.get("/comments").then().statusCode(200);
	}

	@Test(priority=2)
	public void shouldHaveStatus200ForAllCommentsListObject() {
		List<Comment> comments =  commentActions.getComments();
		Assert.assertTrue(!comments.isEmpty());
	}
	
	@Test(priority=3)
	public void createCommentsTest() {
		Comment comment = new Comment();
		comment.setName(FAKER.crypto().md5());
		comment.setEmail(FAKER.internet().emailAddress());
		comment.setUsername(createdUser.getUsername());
		comment.setBody(FAKER.lorem().sentence(20));
		comment.setPostId(createdPost.getId());
		
		createdComment = commentActions.addNewComment(comment);
		
		Assert.assertTrue(comment.getBody().equals(createdComment.getBody()));
	}
	
	@Test(priority=4)
	public void getCommentsTest() {
		Comment comment = commentActions.getComment(createdComment.getId());
		Assert.assertTrue(comment.getBody().equals(createdComment.getBody()));
	}
	
	@Test(priority=5)
	public void updateCommentsTest() {
		Comment comment = commentActions.getComment(createdComment.getId());
		comment.setBody(FAKER.lorem().sentence(15));
		commentActions.updateComment(comment);
		Comment updatedcomment = commentActions.getComment(createdComment.getId());
		
		Assert.assertTrue(updatedcomment.getBody().equals(comment.getBody()));
	}
	
	
	@Test(priority=6)
	public void deleteCommentsTest() {
		commentActions.deleteComment(createdComment);
		Assert.assertFalse(commentActions.isCommentExists(createdComment));
	}
	
	@AfterClass
	public void afterClass() {	
		postActions.getPost(createdPost.getId());
		postActions.deletePost(createdPost);
	}
	

}
