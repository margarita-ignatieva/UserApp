package com.userregistration.app.demo.mapper;

import static com.userregistration.app.demo.model.Status.ACTIVE;
import static com.userregistration.app.demo.model.Status.INACTIVE;

import com.userregistration.app.demo.dto.UserAccountDto;
import com.userregistration.app.demo.model.UserAccount;
import com.userregistration.app.demo.repository.UserAccountRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAccount userAccountFromDto(UserAccountDto userAccountDto) {
        String encryptedPassword = this.passwordEncoder.encode(userAccountDto.getPassword());
        UserAccount userAccount = new UserAccount();
        userAccount.setLastName(userAccountDto.getLastName());
        userAccount.setFirstName(userAccountDto.getFirstName());
        userAccount.setRole(userAccountDto.getRole());
        userAccount.setPassword(encryptedPassword);
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccount.setEnabled(mapStatusToEnabled(userAccountDto.getStatus()));
        userAccount.setUsername(userAccountDto.getUsername());
        userAccountRepository.save(userAccount);
        return userAccount;

    }

    public UserAccountDto userAccountToDto(UserAccount userAccount) {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(userAccount.getId());
        userAccountDto.setUsername(userAccount.getUsername());
        userAccountDto.setFirstName(userAccount.getFirstName());
        userAccountDto.setLastName(userAccount.getLastName());
        userAccountDto.setRole(userAccount.getRole());
        userAccountDto.setPassword(userAccount.getPassword());
        userAccountDto.setStatus(mapEnabledToStatus(userAccount.isEnabled()));
        userAccountDto.setCreatedAt(userAccount.getCreatedAt());
        return userAccountDto;
    }

    private boolean mapStatusToEnabled(String status) {
        if (status == ACTIVE) {
            return true;
        } else {
            return false;
        }
    }

    private String mapEnabledToStatus(boolean enabled) {
        if (enabled == true) {
            return ACTIVE;
        } else {
            return INACTIVE;
        }

    }
}
