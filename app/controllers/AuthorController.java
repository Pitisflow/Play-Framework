package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class AuthorController extends Controller {


    public Result retrieveAuthor(String authorID)
    {

        return Results.ok();
    }



    public Result getAuthors()
    {

        return Results.ok();
    }
}
