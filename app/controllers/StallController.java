package controllers;

import models.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Authentication;
import utils.CatException;
import views.html.stall.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StallController extends Controller {

    /*
     * HTML Methods.
     */

    public static Result sidebar() {
        return ok(sidebar.render());
    }

    public static Result dashboard() {
        return ok(dashboard.render());
    }

    public static Result menu() {
        return ok(menu.render());
    }

    public static Result dish() {
        return ok(dish.render());
    }

    public static Result category() {
        return ok(category.render());
    }

    public static Result orders() {
        return ok(orders.render());
    }

    public static Result report() {
        return ok(report.render());
    }

    public static Result records() {
        return ok(records.render());
    }

    public static Result settings() {
        return ok(settings.render());
    }

    public static Result reportpage() {
        return ok(reportpage.render());
    }

    public static Result help() {
        return ok(help.render());
    }

    /*
     * JSON Methods.
     */

    @Authentication
	public static Result auth() {
        ObjectNode result = Json.newObject();

        Manager manager = (Manager) ctx().args.get("manager");
        if (manager.getType() == 1) {
            result.put("error", 1009);
            result.put("message", "This is a system manager account.");
        } else {
            result.put("error", 0);
            result.put("currentManager", Json.toJson(ctx().args.get("manager")));
            result.put("message", "Success.");
        }

        return ok(result);
    }

    @Authentication
    public static Result addCategory() {
    	ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
    	String newCategoryName = data.get("name");
        try {
        	if (data.get("stallId") == null) throw new CatException(4002, "must input stallId");
        	Integer newStallId = Integer.parseInt(data.get("stallId"));
            Category newCategory = new Category(newCategoryName, newStallId);
            result.put("error", 0);
            result.put("newCategory", Json.toJson(newCategory));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication
	public static Result editCategory(Integer categoryId) {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Category category = Category.find.byId(categoryId);
            if (category == null) throw new CatException(4001, "CategoryId does not exist.");
            if (data.get("name") != null) category.setName(data.get("name"));
            if (data.get("stallId") != null) category.setStall(Integer.parseInt(data.get("stallId")));
            if (data.get("displayOption") != null) category.setDisplayOption(Integer.parseInt(data.get("displayOption")));
            if (data.get("sort") != null) category.setSort(Integer.parseInt(data.get("sort")));
            category.save();
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
	}

    @Authentication
    public static Result updateCategorySort() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            JsonNode updateList = Json.parse(data.get("updateList"));
            for (int i = 0; i < updateList.size(); i++) {
                JsonNode updateItem = updateList.get(i);
                Category category = Category.find.byId(updateItem.get("categoryId").getIntValue());
                category.setSort(updateItem.get("sort").getIntValue());
                category.save();
            }
            result.put("error", 0);
        } catch (Exception ex) {
            result.put("error", 1);
            result.put("message", ex.getMessage());
        }
        return ok(result);
    }

    @Authentication
	public static Result addDish() {
		ObjectNode result = Json.newObject();
    	DynamicForm data = Form.form().bindFromRequest();
    	String newName  = data.get("name");
    	String newImage = data.get("image");
    	String newDescription = data.get("description");
        Integer newCategoryId       = null,
                newPromotionalPrice = null,
                newPrice            = null;

        if (data.get("categoryId") != null) {
            newCategoryId = Integer.parseInt(data.get("categoryId"));
        }
        if (data.get("newPromotionalPrice") != null) {
            newPromotionalPrice = Integer.parseInt(data.get("promotionalPrice"));
        }
        if (data.get("price") != null) {
            newPrice = Integer.parseInt(data.get("price"));
        }
        String newOptions = data.get("options");
        try {
            Dish newDish = new Dish();
            newDish.setName(newName);
            newDish.setDescription(newDescription);
            newDish.setImage(newImage);
            newDish.setPromotionalPrice(newPromotionalPrice);
            newDish.setPrice(newPrice);
            newDish.setOptions(newOptions);
            newDish.setCategory(newCategoryId);
            newDish.save();
            result.put("error", 0);
            result.put("newDish", Json.toJson(newDish));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
	}

	public static Result editDish(Integer dishId) {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Dish dish = Dish.find.byId(dishId);
            String newName  = data.get("name");
            String newImage = data.get("image");
            String newDescription = data.get("description");

            if (data.get("categoryId") != null) {
                Integer newCategoryId = Integer.parseInt(data.get("categoryId"));
                dish.setCategory(newCategoryId);
            }
            if (data.get("price") != null && data.get("price").length() > 0) {
                Integer newPrice = Integer.parseInt(data.get("price"));
                dish.setPrice(newPrice);
            }
            if (data.get("promotionalPrice") != null && data.get("promotionalPrice").length() > 0) {
                Integer newPromotionalPrice = Integer.parseInt(data.get("promotionalPrice"));
                dish.setPromotionalPrice(newPromotionalPrice);
            }
            if (data.get("promotionStart") != null && data.get("promotionStart").length() > 0) {
                Integer newPromotionStart = Integer.parseInt(data.get("promotionStart"));
                dish.setPromotionStart(newPromotionStart);
            }
            if (data.get("promotionEnd") != null && data.get("promotionEnd").length() > 0) {
                Integer newPromotionEnd = Integer.parseInt(data.get("promotionEnd"));
                dish.setPromotionEnd(newPromotionEnd);
            }
            String newOptions = data.get("options");
            dish.setName(newName);
            dish.setDescription(newDescription);
            dish.setImage(newImage);
            dish.setOptions(newOptions);
            dish.save();
            result.put("error", 0);
            result.put("dish", Json.toJson(dish));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    @Authentication
    public static Result updateDishSort() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            JsonNode updateList = Json.parse(data.get("updateList"));
            for (int i = 0; i < updateList.size(); i++) {
                JsonNode updateItem = updateList.get(i);
                Dish dish = Dish.find.byId(updateItem.get("dishId").getIntValue());
                dish.setSort(updateItem.get("sort").getIntValue());
                dish.save();
            }
            result.put("error", 0);
        } catch (Exception ex) {
            result.put("error", 1);
            result.put("message", ex.getMessage());
        }
        return ok(result);
    }

	//deleteDish

	public static Result getOneOrder(Integer orderId) {
		ObjectNode result = Json.newObject();
        Order order = Order.find.byId(orderId);
        result.put("error", order == null ? 1 : 0);
        result.put("order", Json.toJson(order));
        return ok(result);
	}

	public static Result getAllOrders() {
		ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            String strStallId = data.get("stallId");
            int stallId;
            if (strStallId != null) {
                stallId = Integer.parseInt(strStallId);
            }
            else {
                throw new CatException(9001,"Stall ID cannot be null");
            }
            List<Order> orderList = Order.find.where("stall_id = " + stallId).findList();
            result.put("error", 0);
            result.put("orders", Json.toJson(orderList));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
	}

    public static Result editStatus() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        System.out.println("here");
        try {
            String strOrderId  = data.get("orderId");
            String strNewStatus = data.get("newStatus");
            int orderId;
            int newStatus;
            if (strOrderId != null) {
                orderId = Integer.parseInt(strOrderId);
            } else throw new CatException(5002,"Invalid order Id");
            if (strNewStatus != null) {
                newStatus = Integer.parseInt(strNewStatus);
            } else throw new CatException(5001,"Invalid status");
            Order order = Order.find.byId(orderId);
            if (order == null ) throw new CatException(5003,"Order does not exit");
            order.setStatus(newStatus);
            System.out.println(orderId+" "+newStatus);
            order.save();
            result.put("error", 0);
            result.put("order", Json.toJson(order));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }

    public static Result editStall() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Integer stallId = Integer.parseInt(data.get("stallId"));
            String name = data.get("name");
            String image = data.get("image");
            Integer prepaidDiscount = Integer.parseInt("0" + data.get("prepaidDiscount"));
            Integer studentDiscount = Integer.parseInt("0" + data.get("studentDiscount"));
            Integer facultyDiscount = Integer.parseInt("0" + data.get("facultyDiscount"));
            Integer staffDiscount = Integer.parseInt("0" + data.get("staffDiscount"));
            Stall stall = Stall.find.byId(stallId);
            stall.setName(name);
            stall.setImage(image);
            stall.setPrepaidDiscount(prepaidDiscount);
            stall.setStudentDiscount(studentDiscount);
            stall.setFacultyDiscount(facultyDiscount);
            stall.setStaffDiscount(staffDiscount);
            stall.save();
            result.put("error", 0);
        } catch (Exception ex) {
            result.put("error", 1);
            result.put("message", ex.getMessage());
        }
        return ok(result);
    }

    public static Result getReportData() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Integer stallId = Integer.parseInt(data.get("stallId"));
            Integer year = Integer.parseInt(data.get("year"));
            Integer month = Integer.parseInt(data.get("month"));
            DateFormat formatter = new SimpleDateFormat("yyyy-M-d");
            Date startTime = formatter.parse("" + year + "-" + month + "-1");
            Date endTime = formatter.parse("" + year + "-" + (month + 1) + "-1");
            List<Order> orders = Order.find
                    .where("stall_id = " + stallId)
                    .findList();
            System.out.println(startTime.getTime());
            System.out.println(endTime.getTime());
            int totalOrder = 0;
            int totalRevenue = 0;
            int prepaidCount = 0, studentCount = 0, facultyCount = 0, staffCount = 0;
            for (Order order : orders) {
                if (startTime.before(order.getCreateTime())
                        && endTime.after(order.getCreateTime())) {
                    totalOrder++;
                    totalRevenue += order.getSubtotal();
                    switch (order.getAccountType()) {
                        case 0:
                            prepaidCount++;
                            break;
                        case 1:
                            studentCount++;
                            break;
                        case 2:
                            facultyCount++;
                            break;
                        case 3:
                            staffCount++;
                            break;
                    }
                }
            }
            result.put("error" , 0);
            result.put("totalOrder" , totalOrder);
            result.put("totalRevenue", totalRevenue);
            result.put("prepaid", String.format("%.2f", 100.0 * prepaidCount / totalOrder));
            result.put("student", String.format("%.2f", 100.0 * studentCount / totalOrder));
            result.put("faculty", String.format("%.2f", 100.0 * facultyCount / totalOrder));
            result.put("staff", String.format("%.2f", 100.0 * staffCount / totalOrder));
        } catch (ParseException e) {
            result.put("error", 2001);
            result.put("message", e.getMessage());
        }
        return ok(result);
    }
}
