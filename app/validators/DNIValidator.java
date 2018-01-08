package validators;

import play.data.validation.Constraints;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Http;

import javax.validation.ConstraintValidator;

public class DNIValidator extends Constraints.Validator<String> implements ConstraintValidator<DNI, String> {


    @Override
    public void initialize(DNI constraintAnnotation) {

    }

    @Override
    public boolean isValid(String object) {
        boolean isLetterValid = false;
        boolean isNumbersValid = false;

        if (object.length() != 9 || !Character.isLetter(object.charAt(8))) return false;
        else {
            isLetterValid = checkLetter(object);
            isNumbersValid = checkNumbers(object);

            if (isLetterValid && isNumbersValid) return true;
            else return false;
        }
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        Messages messages = Http.Context.current().messages();

        return new F.Tuple<String, Object[]>(
                messages.at("invalid_dni"), new Object[]{""});
    }


    private boolean checkNumbers(String dni)
    {
        for(int i = 0; i < dni.length() - 1; i++)
        {
            if (!Character.isDigit(dni.charAt(i))) return false;
        }

        return true;
    }

    private boolean checkLetter(String dni)
    {
        String letter = "";
        String[] letters = {"T", "R", "W", "A", "G", "M", "Y", "F", "P",
                "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        int dniInt = Integer.parseInt(dni.substring(0, 8));
        int rest = 0;

        rest = dniInt % 23;

        letter = letters[rest];

        if (letter.equals("" + dni.toUpperCase().charAt(8))) return true;
        else return false;
    }
}
