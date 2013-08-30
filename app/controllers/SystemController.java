package controllers;

import java.lang.annotation.*;

import org.codehaus.jackson.node.ObjectNode;
import play.data.*;
import play.libs.*;
import play.mvc.*;

import models.*;
import utils.*;

public class SystemController extends Controller {
    @With(Authentication.class)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface requireAuthentication {
        boolean value() default true;
    }

    @requireAuthentication
    public static Result auth() {
        ObjectNode result = Json.newObject();
        result.put("error", 0);
        return ok(result);
    }

    @requireAuthentication
    public static Result addManager() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        String username = data.get("username");
        String password = data.get("password");
        int type, sid;
        if (data.get("type") != null) {
            type = Integer.parseInt(data.get("type"));
        } else {
            type = 0;
        }
        if (data.get("sid") != null) {
            sid = Integer.parseInt(data.get("sid"));
            if (sid != 0) {
                type = 1;
            }
        } else {
            sid = 0;
        }
        try {
            Manager newManager = Manager.add(username, password, type, sid);
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

    public static Result showManager(int mid) {
        return ok("something");
    }

    public static Result listManager() {
        return ok("something");
    }
}
