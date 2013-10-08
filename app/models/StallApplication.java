package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StallApplication {
    @Id
    private Integer stallApplicationId;
    private String stallName;
    private String stallDescription;
    private String contactPerson;
    private String contactNumber;

    public Integer getStallApplicationId() {
        return stallApplicationId;
    }
    public String getStallName() {
        return stallName;
    }
    public String getStallDescription() {
        return stallDescription;
    }
    public String getContactPerson() {
        return contactPerson;
    }
    public String getContactNumber() {
        return contactNumber;
    }

    public void setStallName(String newStallName) {
        stallName = newStallName;
    }
    public void setStallDescription(String newStallDescription) {
        stallDescription = newStallDescription;
    }
    public void setContactPerson(String newContactPerson) {
        contactPerson = newContactPerson;
    }
    public void setContactNumber(String newContactNumber) {
        contactNumber = newContactNumber;
    }
}
