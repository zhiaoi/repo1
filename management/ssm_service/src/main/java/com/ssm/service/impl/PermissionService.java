package com.ssm.service.impl;

import com.ssm.dao.IPermissionDao;
import com.ssm.domain.Permission;
import com.ssm.domain.Product;
import com.ssm.service.IPermissionService;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionService implements IPermissionService {
    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }

    @Override
    public Permission findById(String id) throws Exception {
        return permissionDao.findById(id);
    }



    @Override
    public void deletePermission(String id) throws Exception {
        permissionDao.deleteFromRole_Permission(id);
        permissionDao.deletePermission(id);
    }
}
