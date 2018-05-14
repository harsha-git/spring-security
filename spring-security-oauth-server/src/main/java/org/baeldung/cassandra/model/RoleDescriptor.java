package org.baeldung.cassandra.model;

import lombok.Data;
import org.springframework.data.cassandra.mapping.Indexed;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by harsjaya on 5/14/18.
 */
@Data
@Table("roledescriptor")
public class RoleDescriptor implements Serializable {

    private static final long serialVersionUID = 399278489483777404L;

    @Indexed
    protected String id;

    /**
     * the role name should be unique
     */
    @PrimaryKey
    protected String roleName;


    protected String owner = "system";

    /**
     * to differentiate if the permission is usecase specific or seeded by platform
     */
    private String isSeeded = "true";

    /**
     * The resource key to represent the display name.
     */
    private String resourceDescriptor="";

    /**
     * F1797: Additional RBAC Roles (USING UI)
     * role description
     */
    private String description="";
    /**
     * the list of permissions for this role
     */
    protected Set<String> permissionList = new HashSet<>();

    /**
     * add a permission
     *
     * @param name the name of the permission
     */
    public void addPermission(String name) {
        permissionList.add(name);
    }

    /**
     * remove a permission
     *
     * @param name the permission name
     */
    public void removePermission(String name) {
        permissionList.remove(name);
    }


}

