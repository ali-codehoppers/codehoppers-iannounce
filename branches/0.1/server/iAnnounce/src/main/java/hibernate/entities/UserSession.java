package hibernate.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name = "UserSession.findByName", query = "select u from UserSession u where u.sessionID like ?")

//get session by sessionId
public class UserSession implements Serializable {

    private int s_id;
    private String sessionID;
    private String username;
    private Boolean statuss;

    public UserSession() {
    }

    public UserSession(int s_id, String sessionID, String username, Boolean statuss) {
        this.s_id = s_id;
        this.sessionID = sessionID;
        this.username = username;
        this.statuss = statuss;
    }

    @Id
    @GeneratedValue
    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public Boolean getStatuss() {
        return statuss;
    }

    public void setStatuss(Boolean statuss) {
        this.statuss = statuss;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
