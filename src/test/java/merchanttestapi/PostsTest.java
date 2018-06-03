package merchanttestapi;

import org.test.merchant.config.TestBase;
import org.testng.annotations.Test;

public class PostsTest extends TestBase {

	@Test
	public void shouldHaveStatus200ForAllPostsList() {
		REQUEST.get("/posts").then().statusCode(200);
	}

}
