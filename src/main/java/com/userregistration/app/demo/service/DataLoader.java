package com.userregistration.app.demo.service;

import com.userregistration.app.demo.dto.UserAccountDto;
import com.userregistration.app.demo.model.Role;
import com.userregistration.app.demo.model.Status;
import com.userregistration.app.demo.model.UserAccount;
import com.userregistration.app.demo.repository.UserAccountRepository;
import java.time.LocalDateTime;
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
    private UserAccountService userAccountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetUp) {
            return;
        }
        UserAccount userAccount = new UserAccount("user", "boo",
                "baa", Role.USER, LocalDateTime.now(), passwordEncoder.encode("user"),
                true);
        userAccountRepository.save(userAccount);
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setStatus(Status.ACTIVE);
        userAccountDto.setUsername("admin");
        userAccountDto.setFirstName("Test");
        userAccountDto.setLastName("Test");
        userAccountDto.setPassword("test");
        userAccountDto.setRole(Role.ADMIN);
        userAccountService.add(userAccountDto);
        alreadySetUp = true;

    }
}
