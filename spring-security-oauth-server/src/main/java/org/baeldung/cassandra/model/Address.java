package org.baeldung.cassandra.model;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by harsjaya on 5/12/18.
 */
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 781681527550984785L;

    /**
     * this is a possible unit/floor and is optional (not used for geolocation)
     */
    private String addressLine1;

    /**
     * this is a street #, street name (used for geolocation)
     */
    private String addressLine2;

    /**
     * the city
     */
    private String city;

    /**
     * the province or state
     */
    private String provinceState;

    /**
     * the country
     */
    private String country;

    /**
     * the zip code or postal code
     */
    private String zipPostal;

    /**
     * the home number
     */
    private String homeNumber;

    /**
     * the work number
     */
    private String workNumber;

    /**
     * the mobile number
     */
    private String mobileNumber;


}