package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

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

    public Integer getId() {
        return managerId;
    }

    public void setUsername(String un) {
        username  = un;
    }

    public void setPassword(String pw) {
        password = pw;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer t) {
        type = t;
    }

    public boolean verifyPassword(String pw) {
        return password.equals(pw);
    }

    public static boolean checkUsername(String newUsername) {
        if (newUsername == null) {
            return false;
        }
        Integer numOfUsers = find.where(String.format("username = '%s'", newUsername)).findRowCount();
        return numOfUsers == 0;
    }

    public static Manager add(String username, String password, Integer type, Integer stallId) throws CatException {
        if (username == null || username.length() < 5) {
            throw new CatException(1, "Username must contain at least 5 characters.");
        }
        if (!checkUsername(username)) {
            throw new CatException(2, "Username has been taken.");
        }
        if (password == null || password.length() < 5) {
            throw new CatException(3, "Password must contain at least 5 characters.");
        }
        Manager newManager = new Manager();
        newManager.username = username;
        newManager.setPassword(password);
        if (stallId != null) {
            newManager.stallId = stallId;
        } else {
            newManager.stallId = 0;
        }
        if (type == null) {
            newManager.type = 0;
        } else {
            newManager.type = type;
        }
        newManager.save();
        return newManager;
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
