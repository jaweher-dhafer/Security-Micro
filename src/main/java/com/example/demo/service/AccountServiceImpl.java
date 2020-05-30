package com.example.demo.service;

import com.example.demo.dao.AppRoleRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.AppRole;
import com.example.demo.entities.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional

public class AccountServiceImpl implements  AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository , BCryptPasswordEncoder bCryptPasswordEncoder){
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public AppUser saveUser (String username,String password , String confirmedPassword){
        AppUser  user=appUserRepository.findByUsername(username);
        if(user!=null) throw new RuntimeException("user already exists");
        if(!password.equals(confirmedPassword)) throw new RuntimeException("please cofirm your password");
        AppUser appUser=new AppUser();
        appUser.setUsername(username);
        appUser.setActived(true);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUserRepository.save(appUser);
        addRoleToUser(username ,"User");
        return null;
    }
    @Override
    public AppRole save(AppRole role )
    { return appRoleRepository.save(role);}

    @Override
    public AppUser loadUserByUsername(String username)
    {return appUserRepository.findByUsername(username);}

    @Override
    public void addRoleToUser(String username, String rolename){
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRoleName(rolename);
        appUser.getRoles().add(appRole);
    }
}
