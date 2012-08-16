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
public class AutoCompleteIATACodeValidator extends AbstractValidator<Void, String> {

    public static final String VALID_INPUT_CHARS = "\\([A-Z]{3}\\).*";      //Todo

    public AutoCompleteIATACodeValidator() {
        super(null, String.class, "error.autocompletevaluenotselected");
    }

    public void validate(Field field, Void constraintValue, MessageFormatter formatter, String value)
            throws ValidationException {
        if (value == null) {
            throw new ValidationException(buildMessage(formatter, field, constraintValue));
        } else if (!value.matches(VALID_INPUT_CHARS)) {
            throw new ValidationException(buildMessage(formatter, field, constraintValue));

        }
    }

    public void render(Field field, Void aVoid, MessageFormatter messageFormatter, MarkupWriter markupWriter, FormSupport formSupport) {

    }

    private String buildMessage(MessageFormatter formatter, Field field, Void constraintValue) {
        return formatter.format(constraintValue, field.getLabel());
    }


}
