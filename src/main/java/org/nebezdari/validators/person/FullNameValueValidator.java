package org.nebezdari.validators.person;

import org.nebezdari.Person;
import org.nebezdari.validators.IValidator;
import org.nebezdari.validators.Message;
import org.nebezdari.validators.Status;

import java.util.regex.Pattern;

public class FullNameValueValidator implements IValidator<Person> {
    @Override
    public Message validate(Person objectForValidate) {
        if (!objectForValidate.getClass().equals(getAppliableFor())) {
            return new Message(Status.ERROR, "Incorrect validation");
        }

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        if (pattern.matcher(objectForValidate.getFirstName()).find()) {
            return new Message(Status.ERROR, "First name contains incorrect symbols");
        }

        if (pattern.matcher(objectForValidate.getSecondName()).find()) {
            return new Message(Status.ERROR, "Second name contains incorrect symbols");
        }

        if (pattern.matcher(objectForValidate.getMiddleName()).find()) {
            return new Message(Status.ERROR, "Middle name contains incorrect symbols");
        }

        return new Message(Status.OK, "Person's full name is correct");
    }

    @Override
    public Class<?> getAppliableFor() {
        return Person.class;
    }
}
