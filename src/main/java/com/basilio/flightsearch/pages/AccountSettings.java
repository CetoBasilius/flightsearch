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
    private Authenticator authenticator;

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
        return serviceDAO.findWithNamedQuery(User.ALL);
    }

    public Object onActionFromDelete(long userId) {
        if (authenticator.getLoggedUser().getId() == userId) {
            authenticator.logout();
        }
        serviceDAO.delete(User.class, userId);
        return (AccountSettings.class);
    }

    //TODO return success window or dialog
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

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public ServiceDAO getServiceDAO() {
        return serviceDAO;
    }

    public void setServiceDAO(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public Form getChangePasswordForm() {
        return changePasswordForm;
    }

    public void setChangePasswordForm(Form changePasswordForm) {
        this.changePasswordForm = changePasswordForm;
    }

    public String getVerifyNewPassword1() {
        return verifyNewPassword1;
    }

    public void setVerifyNewPassword1(String verifyNewPassword1) {
        this.verifyNewPassword1 = verifyNewPassword1;
    }

    public String getVerifyNewPassword2() {
        return verifyNewPassword2;
    }

    public void setVerifyNewPassword2(String verifyNewPassword2) {
        this.verifyNewPassword2 = verifyNewPassword2;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Form getRegisterForm() {
        return registerForm;
    }

    public void setRegisterForm(Form registerForm) {
        this.registerForm = registerForm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
