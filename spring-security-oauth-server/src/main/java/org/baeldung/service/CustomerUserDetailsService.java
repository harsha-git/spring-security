package org.baeldung.service;

import org.baeldung.cassandra.model.CustomerUser;
import org.baeldung.cassandra.model.RoleDescriptor;
import org.baeldung.cassandra.repository.CustomerUserRepository;
import org.baeldung.cassandra.repository.RoleDescriptorCassandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by harsjaya on 5/13/18.
 */

public class CustomerUserDetailsService implements UserDetailsService{

    @Autowired
    private CustomerUserRepository customerUserRepository;

    @Autowired
    private RoleDescriptorCassandraRepository roleDescriptorCassandraRepository;

    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
        System.out.println("Calling CustomerUserDetailsService");
        CustomerUser customerUser = customerUserRepository.findByClientId(clientId);
        System.out.println("Calling CustomerUserDetailsService customerUser:"+customerUser);

       /* List<String> authorities = new ArrayList<String>(){{
           add("ADMIN");
        }
        }; */
        Set<String> authorities = getAuthorities(customerUser);

        return new CustomerUserDetails(clientId,customerUser.getClientSecret(),authorities.toArray(new String[authorities.size()]));
    }

    private Set<String> getAuthorities(CustomerUser customerUser){
        Set<String> permissionList = new HashSet<String>();
        Set<String> roleIdSet = customerUser.getRoles();
        List<RoleDescriptor> rolesFromDB = (List<RoleDescriptor>)roleDescriptorCassandraRepository.findAll();
        for(RoleDescriptor roleDescriptor:rolesFromDB){
            if(roleIdSet.contains(roleDescriptor.getId())){
                permissionList.addAll(roleDescriptor.getPermissionList());
            }
        }
        return permissionList;
    }


}
