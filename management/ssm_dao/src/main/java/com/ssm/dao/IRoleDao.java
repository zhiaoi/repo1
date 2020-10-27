package com.ssm.dao;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {
    @Select("select * from role where id in(select roleId from users_role where userId = #{userId})")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "roleName",property = "roleName"),
            @Result(column = "roleDesc",property = "roleDesc"),
            @Result(column = "id",property = "permissions",javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.IPermissionDao.findByRoleId"))
    })
    public Role findRoleByUserId(String userId) throws Exception;

    @Select("select * from role")
    List<Role> findAll() throws Exception;

    @Insert("insert into role(roleName,roleDesc)values(#{roleName},#{roleDesc})")
    void save(Role role) throws Exception;

    @Select("select * from role where id = #{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(id = true,column = "roleName",property = "roleName"),
            @Result(id = true,column = "roleDesc",property = "roleDesc"),
            @Result(id = true,column = "id",property = "permissions",javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.IPermissionDao.findByRoleId"))
    })
    Role findByRoleId(String id) throws Exception;

    @Delete("delete from users_role where roleId =#{roleId}")
    void deleteFromUser_RoleByRoleID(String roleId)throws Exception;
    @Delete("delete from role_permission where roleId=#{roleId}")
    void deleteFromRole_PermissionByRoleID(String roleId) throws Exception;
    @Delete("delete from role where id=#{roleId} ")
    void deleteRoleById(String roleId) throws Exception;

    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findRoleByIdAndAllPermission(String roleId);

    @Insert("insert into role_permission(permissionId,roleId) values(#{permissionId},#{roleId})")
    void addPermissionToRole(@Param(value = "roleId") String roleId, @Param(value = "permissionId") String permissionId);
}
