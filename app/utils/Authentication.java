package utils;

import models.*;
import org.codehaus.jackson.node.ObjectNode;
import play.data.*;
import play.libs.Json;
import play.mvc.*;

public class Authentication extends Action.Simple {
    public Result call(Http.Context ctx) throws Throwable {
        try {
            DynamicForm data = Form.form().bindFromRequest();
            Manager manager = Manager.verifyManager(data.get("auth_username"), data.get("auth_password"));
            return delegate.call(ctx);
        } catch (CatException e) {
            ObjectNode result = Json.newObject();
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
            return ok(result);
        }
    }
}