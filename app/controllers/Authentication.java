package controllers;

import models.*;
import play.data.*;
import utils.*;
import play.mvc.*;

import java.util.*;

public class Authentication extends Controller {
    public static Result login() {
        try {
            DynamicForm data = Form.form().bindFromRequest();
            LoginSession loginSession = Manager.login(data.get("username"), data.get("password"));
            return ok("Logged in. " + loginSession.getAuthCode());
        } catch (CatException e) {
            return ok(e.getMessage());
        }
    }

    public static Result logout() {
        return ok("something");
    }

}
