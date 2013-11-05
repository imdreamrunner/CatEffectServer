import controllers.routes;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class ControllerTest {
    @Test
    public void htmlDashboard() {
        Result result = callAction(
                routes.ref.SystemController.dashboard()
        );
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Dashboard");
    }

    /*
    @Test
    public void jsonAuth() {
        Map<String,String> data = new HashMap<>();
        data.put("auth_username", "admin");
        data.put("auth_password", "admin");
        Result result = callAction(
                routes.ref.SystemController.auth(),
                fakeRequest().withFormUrlEncodedBody(data)
        );
        assertThat(status(result)).isEqualTo(OK);
        System.out.println(contentType(result));
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        System.out.println(contentAsString(result));
        assertThat(contentAsString(result)).contains("admin");
    }
    */
}
