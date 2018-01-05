package controllers;

import models.Password;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;

public class PasswordController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result resetPassword(String dni, String password) {
        User user = User.findByDni(dni);

        if (user == null) {
            return Results.notFound();
        } else {
            Form<Password> form = formFactory.form(Password.class).fill(new Password(password));

            if (form.hasErrors()) return Results.status(409, form.errorsAsJson());
            else {
                user.deactivatePasswords();
                user.generateNewPassword(password);

                user.save();

                return Results.created();
            }
        }
    }

}
