package com.basilio.flightsearch.entities;


import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;


import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/22/12
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@NamedQueries(
{
        @NamedQuery(name = User.ALL, query = "Select u from User u"),
        @NamedQuery(name = User.BY_USERNAME_OR_EMAIL, query = "Select u from User u where u.username = :username or u.email = :email"),
        @NamedQuery(name = User.BY_CREDENTIALS, query = "Select u from User u where u.username = :username and u.password = :password") })
@Table(name = "users")
public class User
{

    public static final String ALL = "User.all";

    public static final String BY_USERNAME_OR_EMAIL = "User.byUserNameOrEmail";

    public static final String BY_CREDENTIALS = "User.byCredentials";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min = 3, max = 15)
    private String username;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 50)
    private String fullname;

    @Column(nullable = false)
    @NotNull
    @Email
    private String email;

    @Column(nullable = false)
    @Size(min = 3, max = 12)
    @NotNull
    private String password;

    @Column(nullable = false)
    @NotNull
    private boolean isAdmin;


    public User()
    {
    }

    public User(String fullname, String username, String email)
    {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.isAdmin = false;
    }

    public User(String fullname, String username, String email,
                String password)
    {
        this(fullname, username, email);
        this.password = password;
        this.isAdmin = false;
    }

    public User(String fullname, String username, String email, String password, boolean admin)
    {
        this(fullname, username, email);
        this.password = password;
        this.isAdmin = admin;
    }

    public User(Long id, String username, String fullname, String email, String password)
    {
        super();
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.isAdmin = false;

    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("id ");
        builder.append(id);
        builder.append(",");
        builder.append("username ");
        builder.append(username);
        builder.append(",");
        builder.append("Admin ");
        builder.append(isAdmin);
        return builder.toString();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getFullname()
    {
        return fullname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean isAdmin()
    {
        return isAdmin;
    }

    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
    }


}