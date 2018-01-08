package validators;

import play.data.validation.Constraints;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Http;

import javax.validation.ConstraintValidator;
import java.util.Scanner;

public class UsernameValidator extends Constraints.Validator<String> implements ConstraintValidator<Username, String> {


    @Override
    public void initialize(Username constraintAnnotation) {

    }

    @Override
    public boolean isValid(String object) {
        Scanner scanner = new Scanner(object);
        String validationResult = scanner.findInLine("[^ ()@#ºª{};,.:_+\\-*\\/%]+");

        if (validationResult.length() == object.length()) return true;
        else return false;
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        Messages messages = Http.Context.current().messages();

        return new F.Tuple<String, Object[]>(
                messages.at("invalid_username"), new Object[]{""});
    }
}
