package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Ingredient extends Model{

    @Id
    private Long id;

    private String name;
    private String type;


    public Ingredient() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
