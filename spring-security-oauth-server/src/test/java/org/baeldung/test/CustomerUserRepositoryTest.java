package org.baeldung.test;

import org.baeldung.AuthorizationServerApplication;
import org.baeldung.cassandra.model.CustomerUser;
import org.baeldung.cassandra.repository.CustomerUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by harsjaya on 5/12/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthorizationServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Configuration
@ActiveProfiles("test")
public class CustomerUserRepositoryTest {

    @Autowired
    private CustomerUserRepository customerUserRepository;


    @Test
    public void customerUsersFindAll(){
        List<CustomerUser> customerUsers = customerUserRepository.findAll();
        System.out.println("customerUsersFindAll: "+customerUsers);
        assertNotNull(customerUsers);
    }

}
