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
    private int mid;
    private String username;
    private String password;
    private int type;
    private int sid;

    public static Finder<Long, Manager> find = new Finder<Long, Manager>(
            Long.class, Manager.class
    );

    public int getMid() {
        return mid;
    }

    public void setPassword(String pw) {
        password = pw;
    }

    public boolean verifyPassword(String pw) {
        return password.equals(pw);
    }

    public static boolean checkUsername(String newUsername) {
        if (newUsername == null) {
            return false;
        }
        int numOfUsers = find.where(String.format("username = '%s'", newUsername)).findRowCount();
        return numOfUsers == 0;
    }

    public static Manager add(String username, String password, int type, int sid) throws CatException {
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
        newManager.type = type;
        newManager.sid = sid;
        newManager.save();
        return newManager;
    }

    public static LoginSession login(String username, String password) throws CatException {
        if (username == null) {
            throw new CatException(1, "User is not registered.");
        }
        Manager manager = find.where(String.format("username = '%s'", username)).findUnique();
        if (manager == null) {
            throw new CatException(1, "User is not registered.");
        }
        if (!manager.verifyPassword(password)) {
            throw new CatException(2, "Password is not correct.");
        }
        LoginSession loginSession = new LoginSession(manager.getMid());
        loginSession.save();
        return loginSession;
    }

}
