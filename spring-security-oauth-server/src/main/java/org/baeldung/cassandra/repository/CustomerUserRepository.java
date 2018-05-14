package org.baeldung.cassandra.repository;

import org.baeldung.cassandra.model.CustomerUser;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by harsjaya on 5/12/18.
 */
public interface CustomerUserRepository extends CrudRepository<CustomerUser,String>{
    /**
     * Method for obtaining a specific
     * entry corresponding to the specified clientId.
     *
     * @param clientId unique index for which a VipassClientDetails entry should be retrieved
     * @return ClientDetails entry corresponding to the given clientId
     */
    @Query("SELECT * FROM customeruser WHERE clientid= :clientid ALLOW FILTERING")
    CustomerUser findByClientId(@Param("clientid") String clientId);


    //findAll()
    List<CustomerUser> findAll();

    /** find all existing users (non-deleted)
     *
     */
    @Query("select * from customeruser WHERE deleted=false ALLOW FILTERING;")
    List<CustomerUser> findExistingUsers();

    @Query("SELECT * FROM customeruser  WHERE firstname= :name")
    List<CustomerUser> findByFirstName(@Param("name")  String name);

    @Query("SELECT * FROM customerUser  WHERE email= :email")
    CustomerUser findByEmail(@Param("email") String email);


    @Query("SELECT * FROM customeruser  WHERE id= :id")
    CustomerUser findById(@Param("id") String id);

    @Query("SELECT * FROM customeruser WHERE tenantidset CONTAINS :tenantId ALLOW FILTERING")
    List<CustomerUser> findByTenantId(@Param("tenantId") String tenantId);
}
