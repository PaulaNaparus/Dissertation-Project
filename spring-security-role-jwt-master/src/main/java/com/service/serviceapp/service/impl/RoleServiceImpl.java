package com.service.serviceapp.service.impl;

import com.service.serviceapp.dao.RoleDao;
import com.service.serviceapp.model.Role;
import com.service.serviceapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }
}
