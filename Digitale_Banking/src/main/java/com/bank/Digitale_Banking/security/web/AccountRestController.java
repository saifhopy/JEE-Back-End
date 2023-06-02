package com.bank.Digitale_Banking.security.web;


import lombok.AllArgsConstructor;
import com.bank.Digitale_Banking.security.entities.AppRole;
import com.bank.Digitale_Banking.security.entities.AppUser;
import com.bank.Digitale_Banking.security.entities.RoleUserForm;
import com.bank.Digitale_Banking.security.services.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountRestController {
    private AccountService accountService;

    @GetMapping("/users")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    };

    @PostMapping("/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }
    @PostMapping("/roles")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }

    @PostMapping("/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
         accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
    }
}


