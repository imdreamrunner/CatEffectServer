package controllers;

import models.*;
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

    public static Result prepaidCards() {
        return ok(prepaidcards.render());
    }

    public static Result matriculationCards() {
        return ok(matriculationcards.render());
    }

    public static Result transactions() {
        return ok(transactions.render());
    }

    public static Result accounts() {
        return ok(accounts.render());
    }

    public static Result account() {
        return ok(account.render());
    }

    public static Result qrcode() {
        return ok(qrcode.render());
    }

    public static Result settings() {
        return ok(settings.render());
    }

    public static Result report() {
        return ok(report.render());
    }

    public static Result help() {
        return ok(help.render());
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

    @Authentication
    public static Result addManager() {
        ObjectNode result = Json.newObject();
        try {
            DynamicForm data = Form.form().bindFromRequest();
            String username = data.get("username");
            String password = data.get("password");
            Integer type = null, stallId = null;
            Manager newManager = new Manager();
            if (data.get("type") != null) {
                type = Integer.parseInt(data.get("type"));
                newManager.setType(type);
            }
            if (data.get("stallId") != null) {
                stallId = Integer.parseInt(data.get("stallId"));
                newManager.setStall(stallId);
            }
            newManager.setUsername(username);
            newManager.setPassword(password);
            newManager.save();
            result.put("error", 0);
            result.put("newManager", Json.toJson(newManager));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }  catch (Exception e) {
            result.put("error", 9001);
            result.put("message", "Unknown error");
        }
        return ok(result);
    }

    @Authentication
    public static Result editManager(int managerId) {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Manager manager = Manager.find.byId(managerId);
            if (manager == null) {
                throw new CatException(1001, "Manager not found.");
            }
            manager.setUsername(data.get("username"));
            String password = data.get("password");
            if (password != null && !password.equals("")) {
                manager.setPassword(password);
            }
            manager.save();
            result.put("error", 0);
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        } catch (Exception e) {
            result.put("error", 9001);
            result.put("message", "Unknown error");
        }
        return ok(result);
    }

    @Authentication
    public static Result deleteManager(int managerId) {
        ObjectNode result = Json.newObject();
        try {
            Manager manager = Manager.find.byId(managerId);
            if (manager == null) {
                throw new CatException(3001, "Manager not found.");
            }
            manager.delete();
            result.put("error", 0);
        } catch (CatException ex) {
            result.put("error", ex.getCode());
            result.put("message", ex.getMessage());
        }
        return ok(result);
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
        } else if (strStallId != null) {
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
    public static Result getAllAccounts() {
        ObjectNode result = Json.newObject();
        // DynamicForm data = Form.form().bindFromRequest();
        List<Account> accounts = Account.find.all();
        result.put("error", 0);
        result.put("accounts", Json.toJson(accounts));
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result getOneAccounts(Integer accountId) {
        ObjectNode result = Json.newObject();
        // DynamicForm data = Form.form().bindFromRequest();
        Account account = Account.find.byId(accountId);
        result.put("error", 0);
        result.put("account", Json.toJson(account));
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result getAllMatriculationAccounts() {
        ObjectNode result = Json.newObject();
        // DynamicForm data = Form.form().bindFromRequest();
        List<Account> accounts = Account.find.where("type > 0").findList();
        result.put("error", 0);
        result.put("accounts", Json.toJson(accounts));
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result addPrepaidCard() {
        System.out.println("hi");
        ObjectNode result = Json.newObject();
        try {
            PrepaidCard newPrepaidCard = new PrepaidCard();
            newPrepaidCard.save();
            result.put("error", 0);
            result.put("newPrepaidCard", Json.toJson(newPrepaidCard));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result deletePrepaidCard(int prepaidCardId) {
        ObjectNode result = Json.newObject();
        try {
            PrepaidCard prepaidCard = PrepaidCard.find.byId(prepaidCardId);
            if (prepaidCard == null) {
                throw new CatException(3001, "Prepaid Card not found.");
            }
            prepaidCard.delete();
            result.put("error", 0);
        } catch (CatException ex) {
            result.put("error", ex.getCode());
            result.put("message", ex.getMessage());
        }
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result getAllPrepaidCards() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        List<PrepaidCard> prepaidcardList;
        prepaidcardList = PrepaidCard.find.all();
        result.put("error", 0);
        result.put("prepaidCards", Json.toJson(prepaidcardList));
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result addCanteen() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            String name = data.get("name");
            if (name == null || name.equals("")) {
                throw new CatException(1001, "Canteen name cannot be null.");
            }
            Canteen canteen = new Canteen();
            canteen.setName(name);
            canteen.save();
            result.put("error", 0);
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result editCanteen(int canteenId) {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Canteen canteen = Canteen.find.byId(canteenId);
            if (canteen == null) {
                throw new CatException(1001, "Canteen not found");
            }
            canteen.setName(data.get("name"));
            canteen.setSort(Integer.parseInt(data.get("sort")));
            canteen.save();
            result.put("error", 0);
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result deleteCanteen(int canteenId) {
        ObjectNode result = Json.newObject();
        try {
            Canteen canteen = Canteen.find.byId(canteenId);
            if (canteen.getStallNumber() != 0) {
                throw new CatException(1003, "You must delete all stalls under the canteen.");
            }
            canteen.delete();
            result.put("error", 0);
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        } catch (Exception e) {
            result.put("error", 1111);
            result.put("message", e.getMessage());
        }
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
        if (data.get("sort") != null && data.get("sort") != "") {
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
            if (data.get("sort") != null && data.get("sort") != "") {
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

    @Authentication(requireSystem = true)
    public static Result getAllTransactions() {
        ObjectNode result = Json.newObject();
        List<Transaction> transactionList;
        transactionList = Transaction.find.order("transactionId DESC").findList();
        result.put("error", 0);
        result.put("transactions", Json.toJson(transactionList));
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result getAllTransactionsByAccount(Integer accountId) {
        ObjectNode result = Json.newObject();
        List<Transaction> transactionList;
        transactionList = Transaction.find.where("t0.account_id = " + accountId).order("transactionId DESC").findList();
        result.put("error", 0);
        result.put("transactions", Json.toJson(transactionList));
        return ok(result);
    }

    @Authentication(requireSystem = true)
    public static Result topup() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Integer accountId = Integer.parseInt(data.get("accountId"));
            Integer amount = Integer.parseInt(data.get("amount"));
            Transaction transaction = new Transaction();
            transaction.setAccount(accountId);
            transaction.setAmount(amount);
            transaction.setType(0);
            transaction.save();
            result.put("error", 0);
            result.put("transaction", Json.toJson(transaction));
        } catch (NullPointerException ex) {
            result.put("error", 1);
        }
        return ok(result);
    }

}
