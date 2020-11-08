package com.userregistration.app.demo.service;

import com.userregistration.app.demo.exceptions.UserAlreadyExistException;
import com.userregistration.app.demo.mapper.UserAccountMapper;
import com.userregistration.app.demo.model.UserAccount;
import com.userregistration.app.demo.repository.UserAccountRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                  UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
    }

    @Override
    public UserAccount add(UserAccount userAccount) {
        if (userAccountRepository.findAll().stream()
                .filter(userName -> userName.equals(userAccount.getUsername()))
                .collect(Collectors.toList()).size() > 0) {
            throw new UserAlreadyExistException("User account with username "
                    + userAccount.getUsername() + " already exists!");
        }
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Override
    public List<UserAccount> getUserAccounts() {
        return userAccountRepository.findAll();
    }

    @Override
    public void deleteUserAccount(Long id) {
        userAccountRepository.deleteById(id);
    }

    @Override
    public void updateUserAccount(UserAccount userAccount, Long id) {
        UserAccount userAccountOld = userAccountRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No such element"));
        userAccountOld.setId(id);
        userAccountOld.setUsername(userAccount.getUsername());
        userAccountOld.setEnabled(userAccount.isEnabled());
        userAccountOld.setPassword(userAccount.getPassword());
        userAccountOld.setFirstName(userAccount.getFirstName());
        userAccountOld.setLastName(userAccount.getLastName());
        userAccountOld.setRoles(userAccount.getRoles());
        userAccountRepository.flush();
    }

    @Override
    public UserAccount findById(Long id) {
        return userAccountRepository
                .findById(id).orElseThrow(() ->
                new RuntimeException("No such element"));
    }

}
