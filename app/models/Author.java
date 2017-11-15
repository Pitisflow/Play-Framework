package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Author extends Model{


    @Id
    private Long id;

    private String name;
    private String age;
    private String email;


    public Author() {

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
