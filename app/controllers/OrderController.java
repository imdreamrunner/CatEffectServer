package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.order.feedback;
import views.html.order.menu;

public class OrderController extends Controller {
    public static Result feedback() {
        return ok(feedback.render());
    }

    public static Result viewMenu() {
        return ok(menu.render());
    }

    public static Result calculateTotalPrice() {
        return ok(menu.render());
    }
}



# 一边点菜，一边在controller计算总价，并且传回coffee那边显示出来
# 按pay button的时候，process payment