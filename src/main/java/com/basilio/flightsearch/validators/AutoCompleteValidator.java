package com.basilio.flightsearch.validators;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.validator.AbstractValidator;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/3/12
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoCompleteValidator extends AbstractValidator<Void, String> {
    public AutoCompleteValidator() {
        super(null, String.class, "autocomplete-value-not-selected");
    }

    public void validate(Field field, Void constraintValue, MessageFormatter formatter, String value)
            throws ValidationException {
        if(value==null){
            throw new ValidationException(buildMessage(formatter, field, constraintValue));
        } else {
            if(!value.matches("\\([A-Z]{3}\\).*")){
                throw new ValidationException(buildMessage(formatter, field, constraintValue));
            }
        }
    }

    private String buildMessage(MessageFormatter formatter, Field field, Void constraintValue) {
        return formatter.format(constraintValue, field.getLabel());
    }

    public void render(Field field, Void constraintValue, MessageFormatter formatter, MarkupWriter writer,
                       FormSupport formSupport) {
    }

}
