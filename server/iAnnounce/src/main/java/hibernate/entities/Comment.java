package hibernate.entities;

import java.sql.Timestamp;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name = "Comment.findByName", query = "select c from Comment c where c.a_id_KF = ?")
//get comments by announcement id
public class Comment implements Serializable {

    private int cid;
    private String username_FK;
    private int a_id_KF;
    private String comment;
    private Timestamp ttime;

    public Comment(int cid, String username_FK, int a_id_KF, String comment, Timestamp ttime) {
        this.cid = cid;
        this.username_FK = username_FK;
        this.a_id_KF = a_id_KF;
        this.comment = comment;
        this.ttime = ttime;
    }

    public Comment() {
    }

    @Id
    @GeneratedValue
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getA_id_KF() {
        return a_id_KF;
    }

    public void setA_id_KF(int a_id_KF) {
        this.a_id_KF = a_id_KF;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTtime() {
        return ttime;
    }

    public void setTtime(Timestamp time) {
        this.ttime = time;
    }

    public String getUsername_FK() {
        return username_FK;
    }

    public void setUsername_FK(String username_FK) {
        this.username_FK = username_FK;
    }
}
