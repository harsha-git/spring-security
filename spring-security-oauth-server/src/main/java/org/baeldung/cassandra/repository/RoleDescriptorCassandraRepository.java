package org.baeldung.cassandra.repository;

import org.baeldung.cassandra.model.RoleDescriptor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by harsjaya on 5/14/18.
 */
public interface RoleDescriptorCassandraRepository extends CrudRepository<RoleDescriptor, String> {
}
