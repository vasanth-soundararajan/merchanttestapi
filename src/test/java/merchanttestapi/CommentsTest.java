package merchanttestapi;

import org.test.merchant.config.TestBase;
import org.testng.annotations.Test;

public class CommentsTest  extends TestBase {
	
	@Test
	public void shouldHaveStatus200ForAllCommentsList() {
		REQUEST.get("/comments").then().statusCode(200);
	}

}
