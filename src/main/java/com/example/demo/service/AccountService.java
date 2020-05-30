package com.example.demo.service;

import com.example.demo.entities.AppRole;
import com.example.demo.entities.AppUser;

public interface AccountService {
    public AppUser saveUser(String username,String password ,String confirmedPassword);
    public AppRole save(AppRole role);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username,String rolename);

}
