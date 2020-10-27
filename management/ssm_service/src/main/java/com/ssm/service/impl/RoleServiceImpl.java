package com.ssm.service.impl;

import com.ssm.dao.IRoleDao;
import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import com.ssm.service.IRoleService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;
    @Override
    public List<Role> findAll() throws Exception {
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) throws Exception {
        roleDao.save(role);
    }

    @Override
    public Role findByRoleId(String id) throws Exception {
        return roleDao.findByRoleId(id);
    }

    @Override
    public void deleteRole(String roleId) throws Exception {
        roleDao.deleteFromUser_RoleByRoleID(roleId);
        roleDao.deleteFromRole_PermissionByRoleID(roleId);
        roleDao.deleteRoleById(roleId);
    }

    @Override
    public List<Permission> findOtherPermissions(String id) {
        return roleDao.findRoleByIdAndAllPermission(id);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] permissionsId) {
        for (String permissionId : permissionsId) {
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }
}
