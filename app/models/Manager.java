package models;

import play.db.ebean.Model;
import utils.CatException;

import javax.persistence.*;

@Entity
public class Manager extends Model {
    @Id
    private Integer managerId;
    @Basic(optional = false)
    @Column(unique = true)
    private String username;
    private String password;
    private Integer type = 0; // 0 for stall staffs, 1 for OFS.
    @ManyToOne
    @JoinColumn(name = "stall_id")
    private Stall stall;

    public static Finder<Integer, Manager> find = new Finder<Integer, Manager>(
        Integer.class, Manager.class
    );

    public Integer getManagerId() { return managerId; }
    public String getUsername() { return username; }
    public Integer getType() { return type; }
    public Stall getStall() { return  stall; }

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


    public void setType(Integer newType) {
        if (newType != null) {
            type = newType;
        }
    }

    public void setStall(Stall newStall) {
        stall = newStall;
    }

    public void setStall(Integer newStallId) throws CatException {
        Stall newStall = Stall.find.byId(newStallId);
        if (newStall != null) {
            throw new CatException(5001, "Stall does not exist");
        }
        setStall(newStall);
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
