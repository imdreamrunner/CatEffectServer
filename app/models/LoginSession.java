package models;


import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
public class LoginSession extends Model {
    @Id
    private int lsid;
    private int mid;
    private Date createTime;
    private Date refreshTime;
    private String authCode;

    public LoginSession(int theMid) {
        mid = theMid;
        createTime = refreshTime = new Date();
        authCode = (new AuthCode()).toString();
    }

    public String getAuthCode() {
        return authCode;
    }
}
