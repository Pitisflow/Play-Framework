package validators;

import play.data.validation.Constraints;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Http;

import javax.validation.ConstraintValidator;
import java.util.Scanner;

public class PasswordValidator extends Constraints.Validator<String> implements ConstraintValidator<Password, String> {


    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String object) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigits = false;
        boolean hasLenght = false;

        Scanner scanner = new Scanner(object);

        if (object.length() >= 8) hasLenght = true;
        if (scanner.findInLine("[A-Z]") != null) hasUpper = true;
        if (scanner.findInLine("[a-z]") != null) hasLower = true;
        if (scanner.findInLine("[0-9]") != null) hasDigits = true;


        if (hasUpper && hasLower && hasDigits && hasLenght) return true;
        else return false;
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        Messages messages = Http.Context.current().messages();

        return new F.Tuple<String, Object[]>(
                messages.at("invalid_password"), new Object[]{""});
    }
}
