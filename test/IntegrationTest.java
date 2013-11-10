import org.codehaus.jackson.JsonNode;
import org.junit.Test;
import play.libs.WS;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class IntegrationTest {

    @Test
    public void intergrationTest() {
        running(testServer(3333), new Runnable() {
            public void run() {
                assertThat(
                        WS.url("http://localhost:3333").get().get().getStatus()
                ).isEqualTo(OK);

            }
        });
    }


    @Test
    public void systemLogin() {
        running(testServer(3333), new Runnable() {
            public void run() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("userName", "aUsername");
                parameters.put("password", "secret");
                JsonNode response =
                WS.url("http://localhost:3333/system/auth")
                  .setQueryParameter("username", "admin")
                  .setQueryParameter("password", "admin")
                   .post("content").get().asJson();
                System.out.println(response);

            }
        });
    }
}
