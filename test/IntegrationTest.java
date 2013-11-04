import org.junit.Test;
import play.libs.WS;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class IntegrationTest {

    @Test
    public void startServer() {
        running(testServer(3333), new Runnable() {
            public void run() {
                assertThat(
                        WS.url("http://localhost:3333").get().get().getStatus()
                ).isEqualTo(OK);
            }
        });
    }
  
}
