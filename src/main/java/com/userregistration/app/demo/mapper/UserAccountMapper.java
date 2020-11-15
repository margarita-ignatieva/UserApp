package com.userregistration.app.demo.mapper;

import com.userregistration.app.demo.dto.UserAccountDto;
import com.userregistration.app.demo.model.UserAccount;
import com.userregistration.app.demo.repository.RoleRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAccount userAccountFromDto(UserAccountDto userAccountDto) {
        String encryptedPassword = this.passwordEncoder.encode(userAccountDto.getPassword());
        UserAccount userAccount = new UserAccount();
        userAccount.setLastName(userAccountDto.getLastName());
        userAccount.setFirstName(userAccountDto.getFirstName());
        userAccount.setRoles(Set.of(roleRepository.findByRoleName(userAccountDto.getRoleName())));
        userAccount.setPassword(encryptedPassword);
        userAccount.setCreatedAt(userAccountDto.getCreatedAt());
        userAccount.setEnabled(mapStatusToEnabled(userAccountDto.getStatus()));
        userAccount.setUsername(userAccountDto.getUsername());
        return userAccount;

    }

    public UserAccountDto userAccountToDto(UserAccount userAccount) {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(userAccount.getId());
        userAccountDto.setUsername(userAccount.getUsername());
        userAccountDto.setFirstName(userAccount.getFirstName());
        userAccountDto.setLastName(userAccount.getLastName());
        userAccountDto.setRoleName(userAccount.getRoles().stream()
                            .map(role -> role.getName()).findFirst().orElseThrow());
        userAccountDto.setPassword(userAccount.getPassword());
        userAccountDto.setStatus(mapEnabledToStatus(userAccount.isEnabled()));
        userAccountDto.setCreatedAt(userAccount.getCreatedAt());
        return userAccountDto;
    }

    private boolean mapStatusToEnabled(String status) {
        if (status.equals("INACTIVE")) {
            return false;
        } else {
            return true;
        }
    }

    private String mapEnabledToStatus(boolean enabled) {
        if (enabled == true) {
            return "ACTIVE";
        } else {
            return "INACTIVE";
        }

    }
}
