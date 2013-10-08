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
import views.html.system.*;

import java.util.List;

public class SystemController extends Controller {

    /*
     * HTML Methods.
     */

    public static Result sidebar() {
        return ok(sidebar.render());
    }

    public static Result dashboard() {
        return ok(dashboard.render());
    }

    public static Result managers() {
        return ok(managers.render());
    }

    public static Result stalls() {
        return ok(stalls.render());
    }

    public static Result stall() {
        return ok(stall.render());
    }

    /*
     * JSON Methods.
     */

    @Authentication(requireSystem = true)
    public static Result auth() {
        ObjectNode result = Json.newObject();

        Manager manager = (Manager) ctx().args.get("manager");
        if (manager.getType() == 0) {
            result.put("error", 1009);
            result.put("message", "This is a system manager account.");
        } else {
            result.put("error", 0);
            result.put("currentManager", Json.toJson(ctx().args.get("manager")));
            result.put("message", "Success.");
        }

        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result addManager() {
        ObjectNode result = Json.newObject();
        try {
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
            Manager newManager = new Manager();
            newManager.setUsername(username);
            newManager.setPassword(password);
            newManager.setType(type);
            newManager.setStall(stallId);
            newManager.save();
            result.put("error", 0);
            result.put("newManager", Json.toJson(newManager));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result editManager(int managerId) {
        ObjectNode result = Json.newObject();
        return ok("something");
    }

    @Authentication(requireSystem = true)
    public static Result deleteManager(int managerId) {
        return ok("something");
    }

    @Authentication(requireSystem = true)
    public static Result getOneManager(int managerId) {
        ObjectNode result = Json.newObject();
        Manager manager = Manager.find.byId(managerId);
        result.put("error", manager == null ? 1 : 0);
        result.put("manager", Json.toJson(manager));
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result getAllManagers() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        String strType = data.get("type");
        List<Manager> managerList;
        if (strType == null) {
            managerList = Manager.find.all();
        } else {
            Integer type = Integer.parseInt(strType);
            managerList = Manager.find.where("type = " + type).findList();
        }
        result.put("error", 0);
        result.put("managers", Json.toJson(managerList));
        return ok(result);
    }

    @Authentication(requireSystem = true)
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
            Stall newStall = new Stall();
            newStall.setName(newStallName);
            newStall.setDescription(newDescription);
            newStall.setImage(newImage);
            newStall.setCanteen(newCanteenId);
            result.put("error", 0);
            result.put("newStall", Json.toJson(newStall));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result editStall(Integer stallId) {
        //find the row by id
        //form to get new information
        //check what information has been changed
        return ok("TODO");
    }
}
