package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.PagedList;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.List;

public class AuthorController extends Controller {



    public Result retrieveAuthor(Integer id)
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


    public Result getAuthorCollection(int page)
    {
        PagedList<User> usersList = User.findAll(page);

        return showAuthorsResult(usersList, page, 15);
    }



    public Result getAuthorsFiltered(){
        PagedList<User> usersList;

        String nickFilter = request().getQueryString("nick");
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




        if (nickFilter != null) {
            usersList = User.findByNick(nickFilter, page, pageSize);
        }
        else if (nameFilter != null && surnameFilter != null) {
            usersList = User.findByCompleteName(nameFilter, surnameFilter, page, pageSize);
        } else if (nameFilter != null) {
            usersList = User.findByName(nameFilter, page, pageSize);
        }
        else if (surnameFilter != null) {
            usersList = User.findBySurname(surnameFilter, page, pageSize);
        }
        else return getAuthorCollection(page);

        return showAuthorsResult(usersList, page, pageSize);
    }


    private Result showAuthorsResult(PagedList<User> usersList, int page, int pageSize)
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
