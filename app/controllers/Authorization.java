package controllers;

import models.APIkey;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Optional;

public class Authorization extends Security.Authenticator {


    @Override
    public String getUsername(Http.Context ctx) {
        Optional<String> auth = ctx.request().header("Authentication");

        if (auth.isPresent())
        {
            String token = auth.get();
            APIkey apiKey = APIkey.checkActiveAPIkey(token);

            if (apiKey != null)
            {
                return null;
            }

            return apiKey.getUser().getDni();
        } else return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized();
    }
}
