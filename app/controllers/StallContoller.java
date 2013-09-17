package controllers;

import models.Category;
import models.Dish;
import models.Order;
import org.codehaus.jackson.node.ObjectNode;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Authentication;
import utils.CatException;

import java.util.List;

@Authentication
public class StallContoller extends Controller {

	public static Result auth() {
        ObjectNode result = Json.newObject();
        result.put("error", 0);
        result.put("stall", ctx().args.get("stall").toString());
        result.put("message", "Success.");
        return ok(result);
    }

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

	public static Result editCategory(Integer categoryId) {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            Category category = Category.find.byId(categoryId);
            if (category == null) throw new CatException(4001, "CategoryId does not exist.");
            if (data.get("name") != null) category.setName(data.get("name"));
            if (data.get("stallId") != null) category.setStallId(Integer.parseInt(data.get("stallId")));
            if (data.get("displayOption") != null) category.setStallId(Integer.parseInt(data.get("displayOption")));
            if (data.get("sort") != null) category.setStallId(Integer.parseInt(data.get("sort")));
            category.save();
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);

	}
	/*
        private Integer categoryId;
    	private String name;
    	private Integer stallId;
    	private Integer displayOption = 0;
    	private Integer sort = 0;
    */

	//deleteCategory(categoryId)

	public static Result getAllCategories() {
        ObjectNode result = Json.newObject();
        List<Category> categoryList = Category.find.all();
        result.put("error", 0);
        result.put("categories", Json.toJson(categoryList));
        return ok(result);
	}

	public static Result getOneCategory(Integer categoryId) {
		ObjectNode result = Json.newObject();
        Category category = Category.find.byId(categoryId);
        result.put("error", category == null ? 1 : 0);
        result.put("category", Json.toJson(category));
        return ok(result);
	}

	public static Result addDish() {
		ObjectNode result = Json.newObject();
    	DynamicForm data = Form.form().bindFromRequest();
    	String newName = data.get("name");
    	String newImage = data.get("image");
    	String newDescription = data.get("description");
    	//if (data.get("stallId") != null) {
            Integer newStallId = Integer.parseInt(data.get("stallId"));
        //}
        //if (data.get("categoryId") != null) {
            Integer newCategoryId = Integer.parseInt(data.get("categoryId"));
        //}
        //if (data.get("listPrice") != null) {
            Integer newListPrice = Integer.parseInt(data.get("listPrice"));
        //}
        //if (data.get("price") != null) {
            Integer newPrice = Integer.parseInt(data.get("price"));
        //}
        String newOptions = data.get("options");
        try {
            Dish newDish = new Dish(newName, newImage, newDescription, newStallId, newCategoryId, 
        						 newListPrice, newPrice, newOptions);
            result.put("error", 0);
            result.put("newDish", Json.toJson(newDish));
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
	}

	//public static editDish(Integer dishId) 

	//deleteDish

	public static Result getAllDishes() {
		ObjectNode result = Json.newObject();
        List<Dish> dishList = Dish.find.all();
        result.put("error", 0);
        result.put("dishes", Json.toJson(dishList));
        return ok(result);
	}

	public static Result getOneDish(Integer dishId) {
		ObjectNode result = Json.newObject();
        Dish dish = Dish.find.byId(dishId);
        result.put("error", dish == null ? 1 : 0);
        result.put("dish", Json.toJson(dish));
        return ok(result);
	}

	public static Result getOneOrder(Integer orderId) {
		ObjectNode result = Json.newObject();
        Order order = Order.find.byId(orderId);
        result.put("error", order == null ? 1 : 0);
        result.put("order", Json.toJson(order));
        return ok(result);
	}

	public static Result getAllOrders() {
		ObjectNode result = Json.newObject();
        List<Order> orderList = Order.find.all();
        result.put("error", 0);
        result.put("orders", Json.toJson(orderList));
        return ok(result);
	}
	

}
