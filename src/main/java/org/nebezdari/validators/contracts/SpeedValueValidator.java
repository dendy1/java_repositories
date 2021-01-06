package org.nebezdari.validators.contracts;

import org.nebezdari.contracts.Contract;
import org.nebezdari.contracts.InternetContract;
import org.nebezdari.validators.IValidator;
import org.nebezdari.validators.Message;
import org.nebezdari.validators.Status;

public class SpeedValueValidator implements IValidator<Contract> {
    @Override
    public Message validate(Contract objectForValidate) {
        if (!objectForValidate.getClass().equals(getAppliableFor())) {
            return new Message(Status.ERROR, "Incorrect validation");
        }

        InternetContract internetContract = (InternetContract) objectForValidate;

        if (internetContract.getBps() < 0) {
            return new Message(Status.ERROR, "Speed value must be positive");
        }
        else if (internetContract.getGbps() > 100) {
            return new Message(Status.WARNING, "Speed value is high");
        }

        return new Message(Status.OK, "Speed value passed");
    }

    @Override
    public Class<?> getAppliableFor() {
        return InternetContract.class;
    }
}
