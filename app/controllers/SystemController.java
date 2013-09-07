package controllers;

import org.codehaus.jackson.node.ObjectNode;
import play.data.*;
import play.libs.*;
import play.mvc.*;

import models.*;
import utils.*;

@Authentication
public class SystemController extends Controller {

    public static Result auth() {
        ObjectNode result = Json.newObject();
        result.put("error", 0);
        result.put("manager", ctx().args.get("manager").toString());
        result.put("message", "Success.");
        return ok(result);
    }

    public static Result addManager() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        String username = data.get("username");
        String password = data.get("password");
        Integer type = null, stallId = null;
        if (data.get("type") != null) {
            type = Integer.parseInt(data.get("type"));
        }
        if (data.get("stallId") != null) {
            stallId = Integer.parseInt(data.get("stallId"));
        }
        try {
            Manager newManager = Manager.add(username, password, type, stallId);
            result.put("error", 0);
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    public static Result editManager(int mid) {
        return ok("something");
    }

    public static Result deleteManager(int mid) {
        return ok("something");
    }

    public static Result getOneManager(int mid) {
        return ok("something");
    }

    public static Result getAllManagers() {
        return ok("something");
    }

    public static Result listManager() {
        return ok("something");
    }
}
