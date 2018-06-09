package merchanttestapi;

import org.test.merchant.actions.UserActions;
import org.test.merchant.model.Address;
import org.test.merchant.model.Geo;
import org.test.merchant.model.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.specification.RequestSpecification;

public class UsersTest {

	UserActions userActions;
	User createdUser;
	Faker FAKER;
	RequestSpecification REQUEST;
	

	@BeforeClass
	public void beforeClass() {	
		userActions = new UserActions();
		FAKER = userActions.FAKER;
		REQUEST = userActions.REQUEST;
	}
	
	@Test(priority=1)
	public void shouldHaveStatus200ForAllUserList() {
		REQUEST.get("/users").then().statusCode(200);
	}
	
	@Test(priority=2)
	public void createuserTest() {
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
		Assert.assertTrue(createdUser.getUsername().equals(user.getUsername()));
	}
	
	
	@Test(priority=3)
	public void getuserTest() {
		User retUser = userActions.getUser(createdUser.getId());
		Assert.assertTrue(retUser.getUsername().equals(createdUser.getUsername()));
	}
	
	@Test(priority=4)
	public void updateuserTest() {
		
		User retUser = userActions.getUser(createdUser.getId());
		
		retUser.setEmail(FAKER.internet().emailAddress());
		userActions.updateUser(retUser);
		
		User retUpdatedUser = userActions.getUser(createdUser.getId());
		
		Assert.assertTrue(retUpdatedUser.getEmail().equals(retUser.getEmail()));
	}
	
	@Test(priority=5)
	public void deleteUsers() {
		
		userActions.deleteUser(createdUser);
		Assert.assertFalse(userActions.isUserExists(createdUser));
	}

}
