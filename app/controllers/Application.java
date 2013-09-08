package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.managerslist;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Welcome to CaMS@BTU!"));
    }

    public static Result system() {
        return ok(managerslist.render());
    }
  
}
