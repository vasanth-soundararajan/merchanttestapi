package merchanttestapi;

import org.test.merchant.actions.PostActions;
import org.test.merchant.actions.UserActions;
import org.test.merchant.model.Address;
import org.test.merchant.model.Geo;
import org.test.merchant.model.Post;
import org.test.merchant.model.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.specification.RequestSpecification;

public class PostsTest {

	PostActions postActions;
	Post createdPost;
	
	UserActions userActions;
	User createdUser;
	
	Faker FAKER;
	RequestSpecification REQUEST;

	@BeforeClass
	public void beforeClass() {
		
		userActions = new UserActions();
		FAKER = userActions.FAKER;
		REQUEST = userActions.REQUEST;
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
		
		postActions = new PostActions();
	}

	@Test(priority=1)
	public void shouldHaveStatus200ForAllPostsList() {
		REQUEST.get("/posts").then().statusCode(200);
	}
	
	@Test(priority=2)
	public void createPostsTest() {
		
		Post post = new Post();
		post.setUserId(createdUser.getId());
		post.setTitle(FAKER.crypto().md5());
		post.setBody(FAKER.lorem().sentence(20));
		createdPost = postActions.addNewPost(post);
		
		Assert.assertTrue(post.getBody().equals(createdPost.getBody()));
	}
	
	@Test(priority=3)
	public void getPostsTest() {
		Post post = postActions.getPost(createdPost.getId());
		Assert.assertTrue(post.getBody().equals(createdPost.getBody()));
	}
	
	@Test(priority=4)
	public void updatePostsTest() {
		Post post = postActions.getPost(createdPost.getId());
		post.setBody(FAKER.lorem().sentence(20));
		
		postActions.updatePost(post);
		Post updatedpost = postActions.getPost(createdPost.getId());
		Assert.assertTrue(post.getBody().equals(updatedpost.getBody()));
	}

	@Test(priority=5)
	public void deletePostsTest() {
		postActions.deletePost(createdPost);
		Assert.assertFalse(postActions.isPostExists(createdPost));
	}
	
	@AfterClass
	public void afterClass() {	
		userActions.deleteUser(createdUser);
	}

}
