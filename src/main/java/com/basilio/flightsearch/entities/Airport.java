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
 * Airport Class, for use on the search page, for the auto-complete text field.
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min = 1, max = 4)
    private String code;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @Column(nullable = false)
    @NotNull
    private int runwayLength;

    @Column(nullable = false)
    @NotNull
    private int runwayHeight;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 50)
    private String country;


    @Column(nullable = false)
    @NotNull
    private int offsetGMT;


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


    public Airport() {
    }

    public Airport(String code, String country, String name) {
        super();
        this.code = code;
        this.country = country;
        this.name = name;
    }

    //TODO: add other constructors

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(code);
        builder.append(") ");
        builder.append(name);
        builder.append(", ");
        builder.append(country);

        return builder.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRunwayLength() {
        return runwayLength;
    }

    public void setRunwayLength(int runwayLength) {
        this.runwayLength = runwayLength;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRunwayHeight() {
        return runwayHeight;
    }

    public void setRunwayHeight(int runwayHeight) {
        this.runwayHeight = runwayHeight;
    }

    public int getOffsetGMT() {
        return offsetGMT;
    }

    public void setOffsetGMT(int offsetGMT) {
        this.offsetGMT = offsetGMT;
    }


    public int getLatitudeDegrees() {
        return latitudeDegrees;
    }

    public void setLatitudeDegrees(int latitudeDegrees) {
        this.latitudeDegrees = latitudeDegrees;
    }

    public int getLatitudeMinutes() {
        return latitudeMinutes;
    }

    public void setLatitudeMinutes(int latitudeMinutes) {
        this.latitudeMinutes = latitudeMinutes;
    }

    public int getLatitudeSeconds() {
        return latitudeSeconds;
    }

    public void setLatitudeSeconds(int latitudeSeconds) {
        this.latitudeSeconds = latitudeSeconds;
    }

    public int getLongitudeDegrees() {
        return longitudeDegrees;
    }

    public void setLongitudeDegrees(int longitudeDegrees) {
        this.longitudeDegrees = longitudeDegrees;
    }

    public int getLongitudeMinutes() {
        return longitudeMinutes;
    }

    public void setLongitudeMinutes(int longitudeMinutes) {
        this.longitudeMinutes = longitudeMinutes;
    }

    public int getLongitudeSeconds() {
        return longitudeSeconds;
    }

    public void setLongitudeSeconds(int longitudeSeconds) {
        this.longitudeSeconds = longitudeSeconds;
    }
}
