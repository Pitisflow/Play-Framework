package controllers;

import models.User;
import play.mvc.*;

public class SessionController extends Controller {

    public Result login(String dni)
    {
        User user = User.findByDni(dni);

        if (user == null) {
            return Results.notFound();
        } else {
            user.getApIkey().setActive(true);
            user.getApIkey().update();

            return ok(user.getApIkey().getAPIKey());
        }
    }


    public Result logout(String dni)
    {
        User user = User.findByDni(dni);

        if (user == null) {
            return Results.notFound();
        } else {
            user.deactivateAPIkeys();

            return ok();
        }
    }
}
