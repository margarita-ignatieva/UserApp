package com.userregistration.app.demo.service;

import com.userregistration.app.demo.model.UserAccount;
import java.util.List;

public interface UserAccountService {
    UserAccount add(UserAccount userAccount);

    UserAccount findByUsername(String username);

    List<UserAccount> getUserAccounts();

    void deleteUserAccount(Long id);

    void updateUserAccount(UserAccount userAccount, Long id);

    UserAccount findById(Long id);
}
