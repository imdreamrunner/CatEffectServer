package utils;

import models.*;
import play.data.*;
import play.mvc.*;

public class Authentication extends Action.Simple {
    public Result call(Http.Context ctx) throws Throwable {
        try {
            DynamicForm data = Form.form().bindFromRequest();
            Manager manager = Manager.verifyManager(data.get("username"), data.get("password"));
            return delegate.call(ctx);
        } catch (CatException e) {
            return ok(e.getMessage());
        }
    }
}