package pp.ua.lifebook.web.user.validation;

import pp.ua.lifebook.web.user.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDto user = (UserDto) obj;

        final String password = user.getPassword();
        if (password == null) return false;
        else                  return password.equals(user.getMatchingPassword());
    }
}
