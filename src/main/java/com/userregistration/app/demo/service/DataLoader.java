package com.userregistration.app.demo.service;

import com.userregistration.app.demo.dto.UserAccountDto;
import com.userregistration.app.demo.mapper.UserAccountMapper;
import com.userregistration.app.demo.model.Role;
import com.userregistration.app.demo.model.UserAccount;
import com.userregistration.app.demo.repository.RoleRepository;
import com.userregistration.app.demo.repository.UserAccountRepository;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetUp = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetUp) {
            return;
        }
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        Role roleUser = new Role();
        roleUser.setName("USER");
        roleRepository.saveAndFlush(roleAdmin);
        roleRepository.saveAndFlush(roleUser);
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("user");
        userAccount.setFirstName("chester");
        userAccount.setLastName("benning");
        userAccount.setEnabled(true);
        userAccount.setPassword(passwordEncoder.encode("user"));
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccount.setRoles(Set.of(roleUser));
        userAccountRepository.save(userAccount);
        UserAccount userAccountAD = new UserAccount();
        userAccountAD.setUsername("adminn");
        userAccountAD.setFirstName("mike");
        userAccountAD.setLastName("benning");
        userAccountAD.setEnabled(true);
        userAccountAD.setPassword(passwordEncoder.encode("user"));
        userAccountAD.setCreatedAt(LocalDateTime.now());
        userAccountAD.setRoles(Set.of(roleAdmin));
        userAccountRepository.save(userAccountAD);
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setStatus("ACTIVE");
        userAccountDto.setUsername("admin");
        userAccountDto.setFirstName("Test");
        userAccountDto.setLastName("Test");
        userAccountDto.setPassword("test");
        userAccountDto.setCreatedAt(LocalDateTime.now());
        userAccountDto.setRoleName(roleAdmin.getName());
        userAccountService.add(userAccountMapper.userAccountFromDto(userAccountDto));
        UserAccountDto userAccountUS1 = new UserAccountDto();
        userAccountUS1.setStatus("ACTIVE");
        userAccountUS1.setUsername("mike");
        userAccountUS1.setFirstName("Mike");
        userAccountUS1.setLastName("Shinoda");
        userAccountUS1.setPassword("test");
        userAccountUS1.setCreatedAt(LocalDateTime.now());
        userAccountUS1.setRoleName(roleUser.getName());
        userAccountService.add(userAccountMapper.userAccountFromDto(userAccountUS1));
        UserAccountDto userAccountAD2 = new UserAccountDto();
        userAccountAD2.setStatus("INACTIVE");
        userAccountAD2.setUsername("fenics");
        userAccountAD2.setFirstName("Fenics");
        userAccountAD2.setLastName("Guitar");
        userAccountAD2.setPassword("guitar");
        userAccountAD2.setCreatedAt(LocalDateTime.now());
        userAccountAD2.setRoleName(roleAdmin.getName());
        userAccountService.add(userAccountMapper.userAccountFromDto(userAccountAD2));
        alreadySetUp = true;

    }
}
