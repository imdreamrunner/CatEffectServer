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

        result.put("error", 0);
        result.put("currentManager", Json.toJson(ctx().args.get("manager")));
        result.put("message", "Success.");

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
        String strStallId = data.get("stallId");
        List<Manager> managerList;
        if (strType != null) {
            Integer type = Integer.parseInt(strType);
            managerList = Manager.find.where("type = " + type).findList();
        } if (strStallId != null) {
            Integer stallId = Integer.parseInt(strStallId);
            managerList = Manager.find.where("stall_id = " + stallId).findList();
        } else {
            managerList = Manager.find.all();
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
        Integer newSort = null;
        Integer newCanteenId = null;
        if (data.get("canteenId") != null) {
            newCanteenId = Integer.parseInt(data.get("canteenId"));
        }
        if (data.get("sort") != null) {
            newSort = Integer.parseInt(data.get("sort"));
        }
        try {
            Stall newStall = new Stall();
            newStall.setName(newStallName);
            newStall.setDescription(newDescription);
            newStall.setImage(newImage);
            newStall.setCanteen(newCanteenId);
            newStall.setSort(newSort);
            newStall.save();
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
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Stall stall = Stall.find.byId(stallId);
            if (stall == null) {
                throw new CatException(2001, "Stall not found.");
            }
            stall.setName(data.get("name"));
            stall.setDescription(data.get("description"));
            if (data.get("sort") != null) {
                stall.setSort(Integer.parseInt(data.get("sort")));
            }
            stall.setImage(data.get("image"));
            if (data.get("canteenId") != null) {
                stall.setCanteen(Integer.parseInt(data.get("canteenId")));
            }
            stall.save();
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result deleteStall(Integer stallId) {
        ObjectNode result = Json.newObject();
        Stall stall = Stall.find.byId(stallId);
        stall.delete();
        result.put("error", 0);
        return ok(result);
    }

}
