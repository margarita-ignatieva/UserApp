package com.userregistration.app.demo.repository;

import com.userregistration.app.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Role findByRoleName(String roleName);
}
