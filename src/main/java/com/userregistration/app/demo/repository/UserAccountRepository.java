package com.userregistration.app.demo.repository;

import com.userregistration.app.demo.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("SELECT u FROM UserAccount u WHERE u.username = ?1")
    UserAccount findByUsername(String username);
}
