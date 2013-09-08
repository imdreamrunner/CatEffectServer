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

	public static Result addStall() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        String newStallName = data.get("stallname");
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
    }

    /*pulbic static Result addCategory() {
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

	getAllDish

	getOneDish

	getOneOrder

	getAllOrder*/

}











}
