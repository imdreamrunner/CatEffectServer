package controllers;

import java.lang.annotation.*;

import org.codehaus.jackson.node.ObjectNode;
import play.data.*;
import play.libs.*;
import play.mvc.*;

import models.*;
import utils.*;

@Authentication
public class StallContoller extends Controller {

	public static Result auth() {
        ObjectNode result = Json.newObject();
        result.put("error", 0);
        result.put("stall", ctx().args.get("stall").toString());
        result.put("message", "Success.");
        return ok(result);
    }

    /*

    pulbic static Result addCategory() {
    	ObjectNode result = Json.newObject();
    	DynamicForm data = Form.form().bindFromRequest();
    	String 
    }

	editCategory(categoryId)

	deleteCategory(categoryId)

	getAllCategories

	getOneCategory(categoryId)

	addDish

	editDish

	deleteDish

	getAllDishes

	getOneDish

	getOneOrder

	getAllOrders
	*/

}
