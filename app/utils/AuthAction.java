package utils;

import models.*;
import org.codehaus.jackson.node.ObjectNode;
import play.data.*;
import play.libs.Json;
import play.mvc.*;

public class AuthAction extends Action<Authentication> {
    public Result call(Http.Context ctx) throws Throwable {
        try {
            DynamicForm data = Form.form().bindFromRequest();
            Manager manager = Manager.verifyManager(data.get("auth_username"), data.get("auth_password"));
            if (configuration.requireSystem()) {
                if (manager.getType() != 1) {
                    throw new CatException(2001, "Require permission of system manager.");
                }
            }
            ctx.args.put("manager", manager);
            return delegate.call(ctx);
        } catch (CatException e) {
            ObjectNode result = Json.newObject();
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
            return ok(result);
        }
    }
}