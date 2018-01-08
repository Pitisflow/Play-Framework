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

}
