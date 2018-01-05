package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.PagedList;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;

public class UserController extends Controller{



    public Result createUser()
    {
        return ok();
    }


    public Result retrieveUser(String dni)
    {
        return ok();
    }


    public Result updateUser(String dni)
    {
       return ok();
    }


    public Result deleteUser(String dni)
    {
       return ok();
    }


    public Result getUserCollection(int page)
    {
        return ok();
    }



    public Result getUsersFiltered(){

        return ok();
    }


}
