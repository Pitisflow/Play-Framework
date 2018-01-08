package validators;

import play.data.validation.Constraints;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Http;

import javax.validation.ConstraintValidator;
import java.util.Scanner;

public class NickValidator extends Constraints.Validator<String> implements ConstraintValidator<Nick, String> {


    @Override
    public void initialize(Nick constraintAnnotation) {

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
                messages.at("invalid_nick"), new Object[]{""});
    }
}
