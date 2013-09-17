package models;

import play.db.ebean.Model;
import utils.CatException;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manager extends Model {
    @Id
    private Integer managerId;
    @Basic(optional = false)
    @Column(unique = true)
    private String username;
    private String password;
    private Integer type; // 0 for stall staffs, 1 for OFS.
    private Integer stallId;

    public static Finder<Integer, Manager> find = new Finder<Integer, Manager>(
        Integer.class, Manager.class
    );

    public Manager(String newUsername, String newPassword, Integer newType, Integer newStallId) throws CatException {
        setUsername(newUsername);
        setPassword(newPassword);
        setStallId(newStallId == null ? 0 : newStallId);
        setType(newType == null? 0 : newType);
        save();
    }

    public Integer getManagerId() { return managerId; }
    public String getUsername() { return username; }
    public Integer getType() { return type; }
    public Integer getStallId() { return  stallId; }

    public void setUsername(String newUsername) throws CatException {
        if (newUsername == null || newUsername.length() < 5) {
            throw new CatException(1, "Username must contain at least 5 characters.");
        }
        if (!checkUsername(newUsername)) {
            throw new CatException(2, "Username has been taken.");
        }
        username  = newUsername;
    }

    public void setPassword(String newPassword) throws CatException {
        if (newPassword == null || newPassword.length() < 5) {
            throw new CatException(3, "Password must contain at least 5 characters.");
        }
        password = newPassword;
    }


    public void setType(Integer t) {
        type = t;
    }

    public void setStallId(Integer newStallId) {
        stallId = newStallId;
    }

    public boolean verifyPassword(String newPassword) {
        return password.equals(newPassword);
    }

    public static boolean checkUsername(String newUsername) {
        if (newUsername == null) {
            return false;
        }
        Integer numOfUsers = find.where(String.format("username = '%s'", newUsername)).findRowCount();
        return numOfUsers == 0;
    }

    public static Manager verifyManager(String username, String password) throws CatException {
        if (username == null) {
            throw new CatException(1001, "Username cannot be empty.");
        }
        Manager manager = find.where(String.format("username = '%s'", username)).findUnique();
        if (manager == null) {
            throw new CatException(1002, "User is not registered.");
        }
        if (!manager.verifyPassword(password)) {
            throw new CatException(1003, "Password is not correct.");
        }
        return manager;
    }
}
