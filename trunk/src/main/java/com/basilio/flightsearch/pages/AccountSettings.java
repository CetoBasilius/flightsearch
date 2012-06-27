package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.dal.QueryParameters;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/22/12
 * Time: 11:07 AM
 * The users dashboard, where users will be able to change their password or manage their account.
 * Administrators can view all users information, delete users and create new users.
 */

public class AccountSettings {

    @Inject
    private Messages messages;

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    Authenticator authenticator;

    //-------------------- Change password ---------------------
    @Component
    private Form changePasswordForm;

    @Property
    @Validate("password")
    private String verifyNewPassword1;

    @Property
    @Validate("password")
    private String verifyNewPassword2;

    @Property
    @Validate("password")
    private String myPassword;

    //------------------ Administrator create user --------------
    @Property
    private User user;

    @Component
    private Form registerForm;

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

    @Property
    private boolean isAdmin;

    //-------------------------------------------------

    public List<User> getUserlist() {
        List<User> userList = serviceDAO.findWithNamedQuery(User.ALL);
        return userList;
    }

    public Object onActionFromDelete(long userId) {
        if (authenticator.getLoggedUser().getId() == userId) {
            authenticator.logout();
        }
        serviceDAO.delete(User.class, userId);
        return (AccountSettings.class);
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "changePasswordForm")
    public void proceedChangePassword() {
        User myUser = serviceDAO.findUniqueWithNamedQuery(
                User.BY_USERNAME_OR_EMAIL,
                QueryParameters.with("username", authenticator.getLoggedUser().getUsername()).and("email", authenticator.getLoggedUser().getEmail()).parameters());
        if (this.myPassword.equals(myUser.getPassword())) {
            if (this.verifyNewPassword1.equals(this.verifyNewPassword2)) {

                myUser.setPassword(verifyNewPassword1);
                serviceDAO.update(myUser);
            } else {
                changePasswordForm.recordError(messages.get("error.verifypassword"));
            }
        } else {
            changePasswordForm.recordError(messages.get("error.wrongchangepassword"));
        }
    }


    @OnEvent(value = EventConstants.SUCCESS, component = "RegisterForm")
    public Object proceedCreateUser() {

        User userVerif = serviceDAO.findUniqueWithNamedQuery(
                User.BY_USERNAME_OR_EMAIL,
                QueryParameters.with("username", username).and("email", email).parameters());

        if (userVerif != null) {
            registerForm.recordError(messages.get("error.userexists"));

            return null;
        }

        User user = new User(fullName, username, email, password, isAdmin);

        serviceDAO.create(user);

        return AccountSettings.class;
    }

}
