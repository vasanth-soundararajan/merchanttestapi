package merchanttestapi;

import org.test.merchant.config.TestBase;
import org.testng.annotations.Test;

public class UsersTest extends TestBase {
	
	@Test
	public void shouldHaveStatus200ForAllUserList() {
		REQUEST.get("/posts").then().statusCode(200);
	}

}
