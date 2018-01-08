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

    @Inject
    FormFactory formFactory;


    public Result createUser()
    {
        Form<User> form = formFactory.form(User.class).bindFromRequest();

        if (form.hasErrors()) return Results.status(409, form.errorsAsJson());

        User user = form.get();

        if (user.validateAndSaveUser()) {
            return Results.created(user.getApIkey().getAPIKey());
        } else {
            Messages messages = Http.Context.current().messages();
            String message = messages.at("existent_user");
            return Results.status(409, new ErrorObject("1", message).toJson());
        }
    }


    public Result retrieveUser(Integer id)
    {
        User user = User.findById(id.intValue());

        if (user == null) return Results.notFound();

        if (request().accepts("application/json"))
        {
            return ok(user.toJson());
        } else if (request().accepts("application/xml"))  {
            return ok(views.xml.user.apply(user));
        } else {
            return Results.status(415);
        }
    }


    public Result updateUser(String dni)
    {
        Form<User> form = formFactory.form(User.class).bindFromRequest();

        if (form.hasErrors()) return Results.status(409, form.errorsAsJson());

        User updated = form.get();
        User old = User.findByDni(dni);

        if (old == null) return Results.notFound();

        updated.setId(old.getId());
        updated.update();

        return ok();
    }


    public Result deleteUser(String dni)
    {
        User user = User.findByDni(dni);

        if (user != null){
            if (!user.delete()) return internalServerError();
        }

        return ok();
    }


    public Result getUserCollection(int page)
    {
        PagedList<User> usersList = User.findAll(page);

        return showUsersResult(usersList, page, 15);
    }



    public Result getUsersFiltered(){
        PagedList<User> usersList;

        String usernameFilter = request().getQueryString("username");
        String nameFilter = request().getQueryString("name");
        String surnameFilter = request().getQueryString("surname");

        int pageSize = 0;
        int page = 0;

        if (request().getQueryString("pageSize") != null) {
            pageSize = Integer.parseInt(request().getQueryString("pageSize"));
        }

        if (request().getQueryString("page") != null) {
            page = Integer.parseInt(request().getQueryString("page"));
        }




        if (usernameFilter != null) {
            usersList = User.findByUsername(usernameFilter, page, pageSize);
        }
        else if (nameFilter != null && surnameFilter != null) {
            usersList = User.findByCompleteName(nameFilter, surnameFilter, page, pageSize);
        } else if (nameFilter != null) {
            usersList = User.findByName(nameFilter, page, pageSize);
        }
        else if (surnameFilter != null) {
            usersList = User.findBySurname(surnameFilter, page, pageSize);
        }
        else return getUserCollection(page);

        return showUsersResult(usersList, page, pageSize);
    }


    private Result showUsersResult(PagedList<User> usersList, int page, int pageSize)
    {
        List<User> users = usersList.getList();

        if (request().accepts("application/json"))
        {
            ObjectNode json = Json.newObject();

            json.put("page", page);
            json.put("pageSize", pageSize);
            json.put("total_users", usersList.getTotalCount());
            json.putPOJO("users", users);

            return ok(json);
        } else if (request().accepts("application/xml")) {
            return ok(views.xml.users.render(users));
        } else {
            return Results.status(415);
        }
    }

}
