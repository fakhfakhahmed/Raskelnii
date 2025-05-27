package tn.ecnam.authorization.repository;

import tn.ecnam.authorization.entity.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<AuthRole, Long> {
    AuthRole findByRoleNameContaining(String roleName);
}
