package org.nebezdari.validators.contracts;

import org.nebezdari.contracts.Contract;
import org.nebezdari.contracts.InternetContract;
import org.nebezdari.contracts.MobileContract;
import org.nebezdari.validators.IValidator;
import org.nebezdari.validators.Message;
import org.nebezdari.validators.Status;

public class MinutesValidator implements IValidator<Contract> {

    @Override
    public Message validate(Contract objectForValidate) {
        MobileContract mobileContract = (MobileContract)objectForValidate;

        if (mobileContract.getMinutesTraffic() < 0) {
            return new Message(Status.ERROR, "Minutes value must be positive");
        }
        else if (mobileContract.getMinutesTraffic() > 3000) {
            return new Message(Status.WARNING, "Minutes value is high");
        }

        return new Message(Status.OK, "Minutes value passed");
    }

    @Override
    public Class<? extends Contract> getAppliableFor() {
        return InternetContract.class;
    }
}
