package com.basilio.flightsearch.pages;


import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.dal.QueryParameters;
import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.security.AuthenticationException;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Registration page so a user can make an account
 * 
 * @author Basilio
 */

@GuestAccess
public class Signup
{

    @Property
    @Validate("username")
    private String username;

    @Property
    @Validate("required, minlength=3, maxlength=50")
    private String fullName;

    @Property
    @Validate("required,email")
    private String email;

    @Property
    @Validate("password")
    private String password;

    @Property
    @Validate("password")
    private String verifyPassword;

    @Inject
    private ServiceDAO ServiceDAO;

    @Component
    private Form registerForm;

    @Inject
    private Messages messages;

    @Inject
    private Authenticator authenticator;

    @SuppressWarnings("unused")
    @InjectPage
    private Signin signin;

    @OnEvent(value = EventConstants.VALIDATE, component = "RegisterForm")
    public void checkForm()
    {
        if (!verifyPassword.equals(password))
        {
            registerForm.recordError(messages.get("error.verifypassword"));
        }
    }


    @OnEvent(value = EventConstants.SUCCESS, component = "RegisterForm")
    public Object proceedSignup()
    {

        User userVerif = ServiceDAO.findUniqueWithNamedQuery(
                User.BY_USERNAME_OR_EMAIL,
                QueryParameters.with("username", username).and("email", email).parameters());

        if (userVerif != null)
        {
            registerForm.recordError(messages.get("error.userexists"));

            return null;
        }

        User user = new User(fullName, username, email, password);

        ServiceDAO.create(user);

        try
        {
            authenticator.login(username, password);
        }
        catch (AuthenticationException ex)
        {
            registerForm.recordError("Authentication process has failed");
            return this;
        }

        return Index.class;
    }
}
