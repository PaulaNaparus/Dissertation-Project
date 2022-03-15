package com.service.serviceapp.service;

import com.service.serviceapp.config.Exception.BusinessException;
import com.service.serviceapp.model.User;
import com.service.serviceapp.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user) throws BusinessException;

    List<User> findAll();

    User findOne(String username);
}
