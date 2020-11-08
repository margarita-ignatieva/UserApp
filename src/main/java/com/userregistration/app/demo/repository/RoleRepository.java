package com.userregistration.app.demo.repository;

import com.userregistration.app.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
