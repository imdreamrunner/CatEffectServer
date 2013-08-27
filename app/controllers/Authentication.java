package controllers;

import models.*;
import utils.*;
import play.mvc.*;

import java.util.*;

public class Authentication extends Controller {
    public static Result login() {
        try {
            Manager m = Manager.add("admin", "admin", 0, 0);
            return ok("One row added");
        } catch (CatException e) {
            return ok(e.getMessage());
        }
    }

    public static Result logout() {
        return ok("something");
    }

}
