package org.baeldung.cassandra.model;


import lombok.Data;

import org.baeldung.model.Address;
import org.springframework.data.cassandra.mapping.Indexed;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

@Data
@Table("customeruser")
public class CustomerUser implements Serializable {

    private static final long serialVersionUID = 5157305246082663767L;

    /**
     * the max attempts to create a login
     */
    //   public final static int MAX_ATTEMPTS_TO_LOGIN = 30000;

    @PrimaryKey
    protected String id;

    ////////////  OAuth2 Client Details  ///////////////////

    @Indexed
    private String clientId;


    private String clientSecret;


    private Set<String> scope = Collections.emptySet();


    private Set<String> resourceIds = Collections.emptySet();


    private Set<String> authorizedGrantTypes = Collections.emptySet();


    private Set<String> registeredRedirectUris = Collections.emptySet();


    private Set<String> autoApproveScopes = Collections.emptySet();

    private List<String> authorities = Collections.emptyList();


    private Integer accessTokenValiditySeconds;


    private Integer refreshTokenValiditySeconds;


    private Map<String, String> additionalInformation = new LinkedHashMap<>();

    /**
     * the corresponding tenant id for this customer, i.e. who it belongs to
     */
    protected Set<String> tenantIdSet = new HashSet<>();

    /**
     * the email address of customer
     */
    @Indexed
    protected String email;

    /**
     * first name
     */
    @Indexed
    private String firstName;

    /**
     * the last name
     */
    @Indexed
    private String lastName;

    /**
     * the type of account
     * this is domain specific and you decide what to do with it
     */
    private String accntType;

    /* locale */
    private String locale = "en_US";

    /////////////  Address /////////////////

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
     * the country code in ISO format, US, CA, etc
     */
    private String countryCodeISO;

    /**
     * the zip code or postal code
     */
    private String zipPostal;


    //////////// Phone ///////////////////

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


    /**
     * the default detenant that we use if no tenant was specified
     */
    private String defaultTenant;


    /**
     * the list of roles that a user has
     */
    protected Set<String> roles = new HashSet<>();


    //////////// general admin //////////////////

    /**
     * used to mark an entry as deleted but keeps this for posterity
     */
    protected boolean deleted = false;

    /**
     * this is a flag that marks the delete data on an account
     */
    protected String deleteDate;

    /**
     * account is currently locked
     */
    protected boolean accountLocked = false;

    /**
     * time when account was locked
     */
    protected String accountLockoutTime;

    /**
     * login failure times
     */
    protected List<String> loginFailure;

    /**
     * Last time when password was changed
     */
    protected String lastLoginTime;


    /**
     * whether this acocunt is active which means was it verified yet?
     */
    protected boolean accountActive = false;

    /**
     * number of attempts in sequence to login
     */
    protected int serialFailedAttempts = 0;

    /**
     * password policy history
     */
    protected Map<String, String> pwdhistory = new HashMap<String, String>();

    /**
     * password policy name
     */
    @SuppressWarnings("squid:S2068")
    protected String pwdpolicyName = "ppolicy_default";

    /**
     * password changed time
     */
    protected String pwdchangedtime;

    /**
     * password graced auth used (after password expiration)
     */
    protected Integer graceauthused;

    protected Map<String, String> preferences = new HashMap<>();
    /**
     * Flag to mark user is seeded default is false.
     */
    private Boolean isSeeded = false;

    /////////// helpers ////////////////

    /**
     * increment the number of failed attempts
     */
    public void incrementFailedAttempts() {
        serialFailedAttempts++;
    }

    /**
     * add a role
     *
     * @param roleId the id of the role to add
     */
    public void addRole(String roleId) {
        roles.add(roleId);
    }

    /**
     * remove  a role
     *
     * @param roleId the id of the role to remove
     */
    public void removeRole(String roleId) {
        roles.remove(roleId);
    }

    /**
     * add a tenant id to the list
     *
     * @param tenantId the tenant id
     */
    public void addTenant(String tenantId) {
        tenantIdSet.add(tenantId);
    }

    public void removeTenant(String tenantId) {
        tenantIdSet.remove(tenantId);
    }


    //////////// general admin //////////////////


    public Address getAddress() {
        Address address = new Address();
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setCity(city);
        address.setProvinceState(provinceState);
        address.setZipPostal(zipPostal);
        address.setCountry(country);
        address.setHomeNumber(homeNumber);
        address.setWorkNumber(workNumber);
        address.setMobileNumber(mobileNumber);
        return address;
    }

    public void setAddress(Address address) {
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.city = address.getCity();
        this.provinceState = address.getProvinceState();
        this.country = address.getCountry();
        this.zipPostal = address.getZipPostal();
        this.homeNumber = address.getHomeNumber();
        this.workNumber = address.getWorkNumber();
        this.mobileNumber = address.getMobileNumber();
    }

}