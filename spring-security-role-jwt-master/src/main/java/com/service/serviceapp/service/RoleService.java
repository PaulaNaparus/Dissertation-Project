package com.service.serviceapp.service;

import com.service.serviceapp.model.Role;

public interface RoleService {
    Role findByName(String name);
}
