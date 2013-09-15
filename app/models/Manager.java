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

    public Integer getManagerId() {
        return managerId;
    }

    public String getUsername() {
        return username;
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

    public Integer getStallId() {
        return  stallId;
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

//限制应该写在setter里面
    public Manager(String newUsername, String newPassword, Integer newType, Integer newStallId) throws CatException {
        if (newUsername == null || newUsername.length() < 5) {
            throw new CatException(1, "Username must contain at least 5 characters.");
        }
        if (!checkUsername(newUsername)) {
            throw new CatException(2, "Username has been taken.");
        }
        if (newPassword == null || newPassword.length() < 5) {
            throw new CatException(3, "Password must contain at least 5 characters.");
        }
        username = newUsername;
        setPassword(newPassword);
        if (newStallId != null) {
            stallId = newStallId;
        } else {
            stallId = 0;
        }
        if (newType != null) {
            type = newType;
        } else {
            type = 0;
        }
        save();
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
