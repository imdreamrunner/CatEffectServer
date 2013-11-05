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
        System.out.println(contentType(result));
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Dashboard");
    }
}
