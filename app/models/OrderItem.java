package models;

import play.db.ebean.Model;

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

    public void setDish(Dish newDish) {dish = newDish;}
    public void setListPrice(Integer newListPrice) {listPrice = newListPrice;}
    public void setPrice(Integer newPrice) {price = newPrice;}
    public void setQuantity(Integer newQuantity) {quantity = newQuantity;}
    public void setNote(String newNote) {note = newNote;}
}
