package com.basilio.flightsearch.validators;

import java.util.Date;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.validator.AbstractValidator;


/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/3/12
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateValidator extends AbstractValidator<Void, Date> {

    public DateValidator() {
        super(null, Date.class, "date-must-be-in-future");
    }

    public void render(Field field, Void constraintValue,
                       MessageFormatter formatter, MarkupWriter writer,
                       FormSupport formSupport) {
    }


    @Log
    public void validate(Field field, Void constraintValue,
                         MessageFormatter formatter, Date value) throws
            ValidationException {
        if (value.before(new Date())) {
            System.out.println("Date is before!!!!!");
            throw new ValidationException(buildMessage(formatter, field));
        }
    }

    private String buildMessage(MessageFormatter formatter, Field field) {
        return formatter.format(field.getLabel());
    }
}
