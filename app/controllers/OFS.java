package controllers;

import java.lang.annotation.*;

import play.data.*;
import play.mvc.*;

import models.*;
import utils.*;

public class OFS extends Controller {
    @With(Authentication.class)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface requireAuthentication {
        boolean value() default true;
    }

    @requireAuthentication
    public static Result test() {
        return ok("test");
    }

    public static Result add() {
        Result result;
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
            result = ok("ok");
        } catch (CatException e) {
            result = ok(e.getMessage());
        }
        return result;
    }

    public static Result edit(int mid) {
        return ok("something");
    }

    public static Result delete(int mid) {
        return ok("something");
    }

    public static Result show(int mid) {
        return ok("something");
    }

    public static Result list() {
        return ok("something");
    }
}
