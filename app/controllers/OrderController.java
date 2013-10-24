package controllers;

import models.Order;
import models.OrderItem;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.CatException;
import views.html.order.feedback;
import views.html.order.menu;

public class OrderController extends Controller {
    public static Result feedback() {
        return ok(feedback.render());
    }

    public static Result viewMenu() {
        return ok(menu.render());
    }

    public static Result calculateTotalPrice() {
        return ok(menu.render());
    }

    public static Result placeOrder() {
        ObjectNode result = Json.newObject();
        DynamicForm data = Form.form().bindFromRequest();
        try {
            String strOrderItems = data.get("orderItems");
            Integer accountId = Integer.parseInt(data.get("accountId").toString());
            Integer subtotal = Integer.parseInt(data.get("subtotal").toString());
            Order order = new Order();
            order.setAccount(accountId);
            order.setSubtotal(subtotal);
            order.save();
            JsonNode nodeOrderItems = Json.parse(strOrderItems);
            for (int i = 0; i < nodeOrderItems.size(); i++) {
                JsonNode objectItem = nodeOrderItems.get(i);
                Integer dishId = Integer.parseInt(objectItem.get("dishId").toString());
                Integer quantity = Integer.parseInt(objectItem.get("quantity").toString());
                Integer price = Integer.parseInt(objectItem.get("price").toString());
                Integer listPrice = Integer.parseInt(objectItem.get("listPrice").toString());
                String note = objectItem.get("note").toString();
                OrderItem orderItem = new OrderItem();
                orderItem.setDish(dishId);
                orderItem.setOrder(order);
                orderItem.setQuantity(quantity);
                orderItem.setPrice(price);
                orderItem.setListPrice(listPrice);
                orderItem.setNote(note);
                orderItem.save();
            }
            result.put("error", 0);
        } catch (NullPointerException npe) {
            result.put("error", 1101);
            result.put("message", "Data not complete.");
        } catch (CatException e) {
            result.put("error", e.getCode());
            result.put("message", e.getMessage());
        }
        return ok(result);
    }
}



// 一边点菜，一边在controller计算总价，并且传回coffee那边显示出来
// 按pay button的时候，process payment