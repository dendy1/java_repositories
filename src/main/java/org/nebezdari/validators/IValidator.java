package org.nebezdari.validators;

import org.nebezdari.contracts.Contract;

public interface IValidator<T> {
    Message validate(T objectForValidate);
    Class<?> getAppliableFor();
}
