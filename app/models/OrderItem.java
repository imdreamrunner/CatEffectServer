package models;

import play.db.ebean.Model;
import utils.CatException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem extends Model {
    @Id
    private Integer orderItemId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;
    private Integer listPrice;
    private Integer price;
    private Integer quantity;
    private String note;

    public Dish getDish() {return dish;}
    public Integer getListPrice() {return listPrice;}
    public Integer getPrice() {return price;}
    public Integer getQuantity() {return quantity;}
    public String getNote() {return note;}
    public Integer getOrderId() { return order != null ? order.getOrderId() :null; }

    public void setDish(Dish newDish) {dish = newDish;}
    public void setDish(Integer newDishId) throws CatException {
        Dish newDish = Dish.find.byId(newDishId);
        if (newDish == null) {
            throw new CatException(6001, "Dish not found.");
        }
        setDish(newDish);
    }
    public void setOrder(Order newOrder) {order = newOrder;}
    public void setOrder(Integer newOrderId) throws CatException {
        Order newOrder = Order.find.byId(newOrderId);
        if (newOrder == null) {
            throw new CatException(6001, "Order not found.");
        }
        setOrder(newOrder);
    }
    public void setListPrice(Integer newListPrice) {listPrice = newListPrice;}
    public void setPrice(Integer newPrice) {price = newPrice;}
    public void setQuantity(Integer newQuantity) {quantity = newQuantity;}
    public void setNote(String newNote) {note = newNote;}
}
