package com.basilio.flightsearch.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/25/12
 * Time: 12:49 PM
 * Airport stub Class, for use on the search page, for the auto-complete text field.
 */

@Entity
@NamedQueries(
        {
                @NamedQuery(name = AirportStub.ALL, query = "Select u from AirportStub u"),
                @NamedQuery(name = AirportStub.BY_CODE, query = "Select u from AirportStub u where u.code = :code")})
@Table(name = "airportstubs")
public class AirportStub {

    public static final String ALL = "AirportStub.all";

    public static final String BY_CODE = "AirportStub.byCode";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Size(min = 1, max = 5)
    private String code;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 140)
    private String descriptor;

    @Column(nullable = false)
    @NotNull
    float latitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Column(nullable = false)
    @NotNull
    float longitude;


    public AirportStub() {
    }

    public AirportStub(String code, String descriptor) {
        super();
        this.code = code;
        this.descriptor = descriptor;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(code);
        builder.append(") ");
        builder.append(descriptor);
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

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

}
