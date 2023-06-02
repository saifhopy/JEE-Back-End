package com.bank.Digitale_Banking.security.services;



import com.bank.Digitale_Banking.security.entities.AppRole;
import com.bank.Digitale_Banking.security.entities.AppUser;

import java.util.List;

public interface AccountService {
     AppUser addNewUser(AppUser appUser);
     AppRole  addNewRole(AppRole appRole);
     void addRoleToUser(String username, String roleName);
    AppUser loadUserByUserName(String username);
    List<AppUser> listUsers();
}
