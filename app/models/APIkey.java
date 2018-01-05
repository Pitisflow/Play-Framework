package models;

import io.ebean.Finder;
import io.ebean.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Random;

@Entity
@Table(name = "APIKeys")
public class APIkey extends BaseModel{

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase();
    private static final String digits = "0123456789";
    private static final String alphanum = upper + lower + digits;


    
    private String APIKey;
    private boolean active;


    @OneToOne
    @JsonIgnore
    private User user;


    public static final Finder<Long, APIkey> find = new Finder<>(APIkey.class);


    public APIkey() {

    }

    public static String generateAPIkey() {
        StringBuilder sb = new StringBuilder(15);
        Random random = new Random();

        for (int i = 0; i < sb.capacity(); i++) {
            sb.append(alphanum.charAt(random.nextInt(alphanum.length())));
        }

        return sb.toString();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
