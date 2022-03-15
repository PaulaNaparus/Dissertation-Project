package com.service.serviceapp.service.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.service.serviceapp.config.Exception.BusinessException;
import com.service.serviceapp.dao.UserDao;
import com.service.serviceapp.model.Role;
import com.service.serviceapp.model.User;
import com.service.serviceapp.model.UserDto;
import com.service.serviceapp.service.RoleService;
import com.service.serviceapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User save(UserDto user) throws BusinessException {


        //exist verify

        if (!Objects.isNull(userDao.findByUsername(user.getUsername()))) {
            throw new BusinessException(401, "Username already exist ");
        }

        if (!Objects.isNull(userDao.findByEmail(user.getEmail()))) {
            throw new BusinessException(401, "Email already exist ");
        }
        //correct input verify

        if (Objects.isNull(user)) {
            throw new BusinessException(401, "Body null");
        }
        if (Objects.isNull(user.getEmail())) {
            throw new BusinessException(401, "Email can't be null");
        }
        if (!Objects.isNull(user.getEmail())) {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(user.getEmail());
            if (!matcher.matches()) {
                throw new BusinessException(401, "Email should be in correct form");
            }
        }

        if (Objects.isNull(user.getPassword())) {
            throw new BusinessException(401, "Password can't be null");
        }

        if (Objects.isNull(user.getUsername())) {
            throw new BusinessException(401, "Username can't be null");
        }

//        Username must start with letter
//        Can contains a-zA-Z0-9 and dot
//        Can't have 2 consecutive dots

        if ((user.getUsername().matches("^[a-zA-Z](?!.*\\\\.\\\\.)[a-zA-Z.\\\\d]"))) {
            throw new BusinessException(401, "Invalid username");
        }

        if (Objects.isNull(user.getName())) {
            throw new BusinessException(401, "Name can't be null");
        }

        if ((user.getName().matches("[a-zA-Z ]*\\d+.*"))) {
            throw new BusinessException(401, "Name should be string ");
        }

        if (Objects.isNull(user.getPhone())) {
            throw new BusinessException(401, "Phone can't be null");
        }

        if (!Objects.isNull(user.getPhone())) {
            String regex = "[0-9]{10}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(user.getPhone());
            if (!matcher.matches()) {
                throw new BusinessException(401, "Phone should be in correct form and 10 digits");
            }
        }

        User nUser = user.getUserFromDto();
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if (nUser.getEmail().split("@")[1].equals("service.ro")) {
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userDao.save(nUser);
    }
}
