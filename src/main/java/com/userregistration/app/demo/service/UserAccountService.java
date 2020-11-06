package com.userregistration.app.demo.service;

import com.userregistration.app.demo.dto.UserAccountDto;
import com.userregistration.app.demo.model.UserAccount;
import java.util.List;

public interface UserAccountService {
    UserAccount add(UserAccountDto userAccountDto);

    UserAccountDto findByUsername(String username);

    List<UserAccount> getUserAccounts();

    void deleteUserAccount(Long id);

    void updateUserAccount(UserAccountDto userAccountDto, Long id);

    UserAccountDto findById(Long id);
}
