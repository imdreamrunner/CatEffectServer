package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.order.feedback;

public class OrderController extends Controller {
    public static Result feedback() {
        return ok(feedback.render());
    }
}
