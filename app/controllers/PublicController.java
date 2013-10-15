package controllers;

import models.Canteen;
import models.Category;
import models.Dish;
import models.Stall;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

import java.io.File;
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

    public static Result getAllCategories(Integer stallId) {
        ObjectNode result = Json.newObject();
        List<Category> categoryList = Category.find.where("stall_id="+stallId).findList();
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

    public static Result getAllDishes(Integer categoryId) {
        ObjectNode result = Json.newObject();
        List<Dish> dishList = Dish.find.where("category_id=" + categoryId).findList();
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

    public static Result uploadImage() {
        ObjectNode result = Json.newObject();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = null;
        if (body != null) {
            picture = body.getFile("image");
        }
        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file = picture.getFile();
            result.put("error", 0);
            result.put("fileName", fileName);
            result.put("contentType", contentType);
        } else {
            result.put("error", 9001);
        }
        return ok(result);
    }
}
