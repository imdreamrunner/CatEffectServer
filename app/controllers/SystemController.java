package controllers;

import models.Manager;
import models.Stall;
import org.codehaus.jackson.node.ObjectNode;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Authentication;
import utils.CatException;
import views.html.system.managers;

import java.util.List;

@Authentication(requireSystem = true)
public class SystemController extends Controller {

    public static Result managers() {
        return ok(managers.render());
    }

    public static Result auth() {
        ObjectNode result = Json.newObject();
        result.put("error", 0);
        result.put("currentManager", Json.toJson(ctx().args.get("manager")));
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
            Manager newManager = new Manager(username, password, type, stallId);
            result.put("error", 0);
            result.put("newManager", Json.toJson(newManager));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    public static Result editManager(int managerId) {
        return ok("something");
    }

    public static Result deleteManager(int managerId) {
        return ok("something");
    }

    public static Result getOneManager(int managerId) {
        ObjectNode result = Json.newObject();
        Manager manager = Manager.find.byId(managerId);
        result.put("error", manager == null ? 1 : 0);
        result.put("manager", Json.toJson(manager));
        return ok(result);
    }

    public static Result getAllManagers() {
        ObjectNode result = Json.newObject();
        List<Manager> managerList = Manager.find.all();
        result.put("error", 0);
        result.put("managerslist", Json.toJson(managerList));
        return ok(result);
    }

    public static Result addStall() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        String newStallName = data.get("stallName");
        String newDescription = data.get("description");
        String newImage = data.get("image");
        Integer newCanteenId = null;
        if (data.get("canteenId") != null) {
            newCanteenId = Integer.parseInt(data.get("canteenId"));
        }
        try {
            Stall newStall = new Stall(newStallName, newDescription, newImage, newCanteenId);
            result.put("error", 0);
            result.put("newStall", Json.toJson(newStall));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    public static Result editStall(Integer stallId) {
        //find the row by id
        //form to get new information
        //check what information has been changed
        return ok("TODO");
    }
}
