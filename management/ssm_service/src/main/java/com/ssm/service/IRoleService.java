package com.ssm.service;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAll() throws Exception;

    void save(Role role) throws Exception;

    Role findByRoleId(String id) throws Exception;

    void deleteRole(String roleId) throws Exception;

    List<Permission> findOtherPermissions(String id);

    void addPermissionToRole(String roleId, String[] permissionId);
}
