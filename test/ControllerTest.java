import controllers.routes;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class ControllerTest {
    @Test
    public void systemControllerTest() {
        Result result = callAction(
                routes.ref.SystemController.dashboard()
        );
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Dashboard");
    }

    @Test
    public void stallControllerTest() {
        Result result = callAction(
                routes.ref.StallController.dashboard()
        );
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Dashboard");
    }

}
