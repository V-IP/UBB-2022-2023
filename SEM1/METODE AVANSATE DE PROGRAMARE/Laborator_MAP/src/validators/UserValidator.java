package validators;

import domain.User;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) throws ValidationException {
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty())
            throw new ValidationException("User invalid!");
    }
}
