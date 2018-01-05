package models;


import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import play.data.validation.Constraints;
import play.libs.Json;
import validators.DNI;
import validators.Username;

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
    @Username
    private String username;

    @Constraints.Required
    @Email
    private String email;
    private int age;


    @JsonIgnore
    @Constraints.Required
    @DNI
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




    public static User findByDni(String dni)
    {
        return find.query()
                .where()
                .eq("dni", dni)
                .findOne();
    }


    public static PagedList<User> findBySurname(String surname, int page, int pageSize)
    {
        return find.query()
                .where()
                .like("surname", "%" + surname + "%")
                .setMaxRows(pageSize)
                .setFirstRow(pageSize*page)
                .findPagedList();
    }


    public static PagedList<User> findByName(String name, int page, int pageSize)
    {
        return find.query()
                .where()
                .like("name", name + "%")
                .setMaxRows(pageSize)
                .setFirstRow(pageSize*page)
                .findPagedList();
    }


    public static PagedList<User> findByCompleteName(String name, String surname, int page, int pageSize)
    {
        return find.query()
                .where()
                .like("name", name + "%")
                .like("surname", "%" + surname + "%")
                .setMaxRows(pageSize)
                .setFirstRow(pageSize*page)
                .findPagedList();

    }


    public static PagedList<User> findByUsername(String username, int page, int pageSize)
    {
        return find.query()
                .where()
                .like("username", username + "%")
                .setMaxRows(pageSize)
                .setFirstRow(pageSize*page)
                .findPagedList();
    }


    public static PagedList<User> findAll(int page)
    {
        return find.query()
                .setMaxRows(15)
                .setFirstRow(15*page)
                .findPagedList();
    }



    public boolean validateAndSaveUser()
    {
        if(User.findByDni(this.dni) == null) {
            Ebean.beginTransaction();

            try {
                this.save();

                deactivatePasswords();
                generateNewPassword();

                deactivateAPIkeys();
                generateAPIkey();

                Ebean.commitTransaction();
            } finally {
                Ebean.endTransaction();
            }

            return true;
        } else return false;
    }


    public void deactivatePasswords() {
        String sql = "UPDATE passwords SET active = 0 WHERE user_id = " + this.getId();
        Ebean.createSqlUpdate(sql).execute();
    }

    public void deactivateAPIkeys() {
        String sql = "UPDATE APIKeys SET active = 0 WHERE user_id = " + this.getId();
        Ebean.createSqlUpdate(sql).execute();
    }


    public void generateNewPassword()
    {
        Password pw = new Password();

        pw.setUser(this);
        pw.setPassword("1234");
        pw.setActive(true);
        this.passwords.add(pw);

        pw.save();
    }



    public void generateAPIkey()
    {
        APIkey apIkey = new APIkey();

        apIkey.setUser(this);
        apIkey.setActive(true);
        apIkey.setAPIKey(APIkey.generateAPIkey());

        this.apIkey = apIkey;

        apIkey.save();
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
