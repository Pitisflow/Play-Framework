package models;


import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.Finder;
import io.ebean.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User extends BaseModel{

    @Constraints.Pattern("[a-zA-Z]")
    private String name;

    @Constraints.Pattern("[a-zA-Z]")
    private String surname;

    @Constraints.Required
    private String username;

    @Constraints.Required
    @Email
    private String email;
    private int age;


    @JsonIgnore
    @Constraints.Required
    private String dni;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Password> passwords;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private APIkey apIkey;


    public static final Finder<Long, User> find = new Finder<>(User.class);


    public User() {
        passwords = new ArrayList<>();
    }





    public JsonNode toJson()
    {
        return Json.toJson(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public APIkey getApIkey() {
        return apIkey;
    }

    public void setApIkey(APIkey apIkey) {
        this.apIkey = apIkey;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
