package controllers;

import models.Canteen;
import models.Stall;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.List;

public class PublicController extends Controller {
  
    public static Result index() {
        return ok(index.render("Welcome to CaMS@BTU!"));
    }

    public static Result getAllCanteens() {
        ObjectNode result = Json.newObject();
        List<Canteen> canteenList = Canteen.find.all();
        result.put("error", 0);
        result.put("canteens", Json.toJson(canteenList));
        return ok(result);
    }

    public static Result getAllStalls() {
        ObjectNode result = Json.newObject();
        List<Stall> stallList = Stall.find.all();
        result.put("error", 0);
        result.put("stalls", Json.toJson(stallList));
        return ok(result);
    }

    public static Result getOneStall(Integer stallId) {
        ObjectNode result = Json.newObject();
        Stall stall = Stall.find.byId(stallId);
        result.put("error", 0);
        result.put("stall", Json.toJson(stall));
        return ok(result);
    }

}
