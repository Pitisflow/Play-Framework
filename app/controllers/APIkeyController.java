package controllers;

import models.APIkey;
import models.User;
import play.mvc.*;


public class APIkeyController extends Controller{

    public Result resetAPIkey(String dni) {
        User user = User.findByDni(dni);

        if (user == null) {
            return Results.notFound();
        } else {
            user.generateNewAPIkey();
            user.save();

            return Results.ok();
        }
    }
}
