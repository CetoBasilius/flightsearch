package com.basilio.flightsearch.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/25/12
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@NamedQueries(
        {
                @NamedQuery(name = Airport.ALL, query = "Select u from Airport u"),
                @NamedQuery(name = Airport.BY_CODE, query = "Select u from Airport u where u.code = :code")})

@Table(name = "airports")
public class Airport {

    public static final String ALL = "Airport.all";

    public static final String BY_CODE = "Airport.byCode";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    @NotNull
    private String code;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private int runwayLength;

    @Column(nullable = false)
    @NotNull
    private int runwayHeight;

    @Column(nullable = false)
    @NotNull
    private String country;

    @Column(nullable = false)
    @NotNull
    private String countryAbbrv;

    @Column(nullable = false)
    @NotNull
    private String offsetGMT;

    @Column(nullable = false)
    @NotNull
    private int latitudeDegrees;

    @Column(nullable = false)
    @NotNull
    private int latitudeMinutes;

    @Column(nullable = false)
    @NotNull
    private int latitudeSeconds;

    @Column(nullable = false)
    @NotNull
    private int longitudeDegrees;

    @Column(nullable = false)
    @NotNull
    private int longitudeMinutes;

    @Column(nullable = false)
    @NotNull
    private int longitudeSeconds;


    public Airport(){}

    //TODO: add other constructors

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("id ");
        builder.append(id);
        builder.append(",");
        builder.append("code ");
        builder.append(code);
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

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getRunwayLength() {
        return runwayLength;
    }

    public int getRunwayHeight() {
        return runwayHeight;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryAbbrv() {
        return countryAbbrv;
    }

    public String getOffsetGMT() {
        return offsetGMT;
    }

    public int getLatitudeDegrees() {
        return latitudeDegrees;
    }

    public int getLatitudeMinutes() {
        return latitudeMinutes;
    }

    public int getLatitudeSeconds() {
        return latitudeSeconds;
    }

    public int getLongitudeDegrees() {
        return longitudeDegrees;
    }

    public int getLongitudeMinutes() {
        return longitudeMinutes;
    }

    public int getLongitudeSeconds() {
        return longitudeSeconds;
    }

}
