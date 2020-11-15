package com.userregistration.app.demo.service;

import com.userregistration.app.demo.model.UserAccount;
import com.userregistration.app.demo.repository.UserAccountRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserAccountServiceImpl service;

    @Mock
    private UserAccountRepository repo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("user");
        userAccount.setFirstName("chester");
        userAccount.setLastName("benning");
        userAccount.setEnabled(true);
        userAccount.setCreatedAt(LocalDateTime.now());

        service.add(userAccount);
        verify(repo, times(1)).save(userAccount);
    }

    @Test
    void getAll() {
        List<UserAccount> accounts = new ArrayList<>();
        accounts.add(new UserAccount());
        accounts.add(new UserAccount());
        accounts.add(new UserAccount());
        when(repo.findAll()).thenReturn(accounts);
        List<UserAccount> accountList = service.getUserAccounts();
        Assert.assertEquals(accounts, accountList);
    }

    @Test
    void delete() {
        List<UserAccount> userAccounts = new ArrayList<>();
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setUsername("user");
        repo.save(userAccount);
        when(repo.findByUsername("user")).thenReturn(userAccount);

        service.deleteUserAccount(1L);
        List<UserAccount> all = repo.findAll();
        Assert.assertEquals(all, new ArrayList<>());
    }
}
