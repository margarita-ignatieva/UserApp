package com.userregistration.app.demo.service;

import com.userregistration.app.demo.dto.UserAccountDto;
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
    public UserAccount add(UserAccountDto userAccountDto) {
        if (userAccountRepository.findAll().stream()
                .map(userAccount -> userAccount.getUsername())
                .filter(userName -> userName.equals(userAccountDto.getUsername()))
                .collect(Collectors.toList()).size() > 0) {
            throw new UserAlreadyExistException("User account with username "
                    + userAccountDto.getUsername() + " already exists!");
        }
        return userAccountRepository.save(userAccountMapper.userAccountFromDto(userAccountDto));
    }

    @Override
    public UserAccountDto findByUsername(String username) {
        return userAccountMapper.userAccountToDto(userAccountRepository.findByUsername(username));
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
    public void updateUserAccount(UserAccountDto userAccountDto, Long id) {
        UserAccount userAccountOld = userAccountRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No such element"));
        UserAccount userAccountNew = userAccountMapper.userAccountFromDto(userAccountDto);
        userAccountOld.setUsername(userAccountNew.getUsername());
        userAccountOld.setEnabled(userAccountNew.isEnabled());
        userAccountOld.setPassword(userAccountNew.getPassword());
        userAccountOld.setFirstName(userAccountNew.getFirstName());
        userAccountOld.setLastName(userAccountNew.getLastName());
        userAccountOld.setRole(userAccountNew.getRole());
        userAccountRepository.flush();
    }

    @Override
    public UserAccountDto findById(Long id) {
        return userAccountMapper.userAccountToDto(userAccountRepository
                .findById(id).orElseThrow(() ->
                new RuntimeException("No such element")));
    }

}
